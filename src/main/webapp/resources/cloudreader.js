function loadMoreItems(currentItem) {
	var unreadItems = currentItem.nextAll('.item');
	var unreadItemsIds = [];
	for (var i = 0; i < unreadItems.length; i++) {
		unreadItemsIds.push(unreadItems[i].id);
	}
	//alert('unread items IDs: ' + unreadItemsIds);
	var downloadedItems = [
		{guid: 'item6', title: 'sixth element'},
		{guid: 'item7', title: 'seventh element'},
		{guid: 'item8', title: 'eighth element'}
	];
	for (var i = 0; i < downloadedItems.length; i++) {
		var lastItem = $('.item').last();
		var lastItemId = lastItem[0].id;
		var newItem = lastItem.clone();
		var downloadedItem = downloadedItems[i];
		newItem[0].id = downloadedItem.guid;
		$('.title', newItem).text(downloadedItem.title);
		newItem.insertAfter(lastItem);
	}
}

function goToItem(itemSelector) {
	var LOAD_THRESHOLD = 2;
	var currentItem = $('.current');
	var newItem;
	if (currentItem.length == 0) {
		newItem = $('.item').first();
	} else {
		newItem = itemSelector(currentItem);
	}
	if (newItem.length == 1) {
		if (!newItem.hasClass('itemRead')) {
			//alert('mark as read: ' + newItem[0].id);
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
	goToItem(function(currentItem) {
		return currentItem.next('.item');
	});
}

function goToPreviousItem() {
	goToItem(function(currentItem) {
		return currentItem.prev('.item');
	});
}
