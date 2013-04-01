(function (cloudReader, $, undefined) {
	
	// public
	
	cloudReader.initHotkeys = function () {
		$(document).bind('keydown', 'n j', goToNextItem);
		$(document).bind('keydown', 'k', goToPreviousItem);
		$(document).bind('keydown', 'v', openItemLink);
		$(document).bind('keydown', 'u', countUnreadItems);
	}


	// private

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
				var readItemId = newItem[0].id;
				$.post('/items/' + readItemId + '/read');
			}
			currentItem.removeClass('itemCurrent');
			newItem.addClass('itemRead');
			newItem.addClass('itemCurrent');
			var remainingCount = newItem.nextAll('.item').length;
			if (remainingCount < LOAD_THRESHOLD) {
				loadMoreItems(newItem);
			}
			newItem[0].scrollIntoView();
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
