(function (cloudReader, $, undefined) {
	
	// public
	
	cloudReader.initHotkeys = function () {
		$(document).keydown(function(event) {
			var SPACE_CODE = 32;
			if (event.keyCode == SPACE_CODE) {
				event.preventDefault();
			}
		});
		$(document).bind('keydown', 'n j space', goToNextItem);
		$(document).bind('keydown', 'k shift+space', goToPreviousItem);
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
		var excludedItemsIds = currentItem.nextAll('.item:not(.itemRead)').map(function () {
			return this.id;
		}).get();
		excludedItemsIds.push(currentItem[0].id);
		$.ajax({
			url: '/items',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(excludedItemsIds)
		}).done(function (downloadedItems) {
			for (var i = 0; i < downloadedItems.length; i++) {
				if ($('#' + downloadedItems[i].guid).length == 0) {
					createNewItem(downloadedItems[i]);
				}
			}
		});
	}

	function switchToNewItem(currentItem, newItem) {
		currentItem.removeClass('itemCurrent');
		newItem.addClass('itemRead');
		newItem.addClass('itemCurrent');
		newItem[0].scrollIntoView();
	}

	function postItemRead(readItemId) {
		$.ajax({
			url: '/items/' + readItemId + '/read',
			type: 'post'
		});
	}

	function goToItem(itemSelector) {
		var LOAD_THRESHOLD = 10;
		var currentItem = $('.itemCurrent');
		var newItem;
		if (currentItem.length == 0) {
			newItem = $('.item').first();
		} else {
			newItem = itemSelector(currentItem);
		}
		if (newItem.length == 1) {
			if (!newItem.hasClass('itemRead')) {
				postItemRead(newItem[0].id);
			}
			switchToNewItem(currentItem, newItem);
			var remainingCount = newItem.nextAll('.item').length;
			if (remainingCount < LOAD_THRESHOLD) {
				loadMoreItems(newItem);
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
