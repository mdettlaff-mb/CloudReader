function loadMoreItems(currentItem) {
	var unreadItemsIds = currentItem.nextAll('.item:not(.itemRead)').map(function() {
		return this.id;
	}).get();
	$.ajax({
		url: "/items",
		type: "post",
		contentType: 'application/json',
		data: JSON.stringify(unreadItemsIds)
	}).done(function (downloadedItems) {
		for (var i = 0; i < downloadedItems.length; i++) {
			var lastItem = $('.item').last();
			var lastItemId = lastItem[0].id;
			var clonedItem = lastItem.clone();
			var downloadedItem = downloadedItems[i];
			clonedItem[0].id = downloadedItem.guid;
			$('.feedTitle', clonedItem).html(downloadedItem.feed.title);
			$('.title', clonedItem).html(downloadedItem.title);
			$('.description', clonedItem).html(downloadedItem.description);
			clonedItem.insertAfter(lastItem);
		}
	});
}

function goToItem(itemSelector) {
	var LOAD_THRESHOLD = 4;
	var currentItem = $('.current');
	var newItem;
	if (currentItem.length == 0) {
		newItem = $('.item').first();
	} else {
		newItem = itemSelector(currentItem);
	}
	if (newItem.length == 1) {
		if (!newItem.hasClass('itemRead')) {
			var readItemId = newItem[0].id;
			$.post("/items/" + readItemId + "/read");
		}
		currentItem.removeClass('current');
		newItem.addClass('itemRead');
		newItem.addClass('current');
		var remainingCount = newItem.nextAll('.item').length;
		if (remainingCount <= LOAD_THRESHOLD) {
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

function initCloudReaderHotkeys() {
	$(document).bind('keydown', 'n j', goToNextItem);
	$(document).bind('keydown', 'k', goToPreviousItem);
}