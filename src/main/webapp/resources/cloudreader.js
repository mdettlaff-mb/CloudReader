(function (cloudReader, $, undefined) {
	
	// public
	
	cloudReader.initHotkeys = function () {
		$(document).bind('keydown', 'n j', goToNextItem);
		$(document).bind('keydown', 'k', goToPreviousItem);
		$(document).bind('keydown', 'v', openItemLink);
		$(document).bind('keydown', 'i', countUnreadItems);
		$(document).bind('keydown', 'd', updateFeeds);
	}


	// private

	function updateFeeds() {
		$.ajax({
			url: '/items/update',
			type: 'post'
		}).done(function (downloadedItemsCount) {
			alert(downloadedItemsCount + ' new items.');
		});
	}

	function countUnreadItems() {
		$.ajax({
			url: '/items/unread/count'
		}).done(function (unreadCount) {
			alert(unreadCount + ' unread items left.');
		});
	}

	function openItemLink() {
		var currentItemLink = $('.itemCurrent .title a').attr('href');
		if (currentItemLink) {
			window.open(currentItemLink);
		}
	}

	function createNewItem(downloadedItem) {
		var lastItem = $('.item').last();
		var lastItemId = lastItem[0].id;
		var clonedItem = lastItem.clone();
		clonedItem.removeClass('itemCurrent');
		clonedItem.removeClass('itemRead');
		clonedItem[0].id = downloadedItem.guid;
		$('.feedTitle a', clonedItem).html(downloadedItem.feed.title);
		$('.feedTitle a', clonedItem).attr('href', downloadedItem.feed.link);
		$('.title a', clonedItem).html(downloadedItem.title);
		$('.title a', clonedItem).attr('href', downloadedItem.link);
		$('.description', clonedItem).html(downloadedItem.description);
		$('.date', clonedItem).html(new Date(parseInt(downloadedItem.date)).format('yyyy-mm-dd HH:MM'));
		clonedItem.insertAfter(lastItem);
	}
	
	function loadMoreItems(currentItem) {
		var unreadItemsIds = currentItem.nextAll('.item:not(.itemRead)').map(function () {
			return this.id;
		}).get();
		$.ajax({
			url: '/items',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(unreadItemsIds)
		}).done(function (downloadedItems) {
			for (var i = 0; i < downloadedItems.length; i++) {
				createNewItem(downloadedItems[i]);
			}
		});
	}

	function switchToNewItem(currentItem, newItem) {
		var LOAD_THRESHOLD = 10;
		currentItem.removeClass('itemCurrent');
		newItem.addClass('itemRead');
		newItem.addClass('itemCurrent');
		var remainingCount = newItem.nextAll('.item').length;
		if (remainingCount < LOAD_THRESHOLD) {
			loadMoreItems(newItem);
		}
		newItem[0].scrollIntoView();
	}

	function goToItem(itemSelector) {
		var currentItem = $('.itemCurrent');
		var newItem;
		if (currentItem.length == 0) {
			newItem = $('.item').first();
		} else {
			newItem = itemSelector(currentItem);
		}
		if (newItem.length == 1) {
			if (!newItem.hasClass('itemRead')) {
				var readItemId = newItem[0].id;
				$.ajax({
					url: '/items/' + readItemId + '/read',
					type: 'post'
				}).done(function () {
					switchToNewItem(currentItem, newItem);
				});
			} else {
				switchToNewItem(currentItem, newItem);
			}
		}
	}
	
	function goToNextItem() {
		goToItem(function (currentItem) {
			return currentItem.next('.item');
		});
	}
	
	function goToPreviousItem() {
		goToItem(function (currentItem) {
			return currentItem.prev('.item');
		});
	}

} (window.cloudReader = window.cloudReader || {}, jQuery));
