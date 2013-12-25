(function (cloudReader, $, undefined) {
	
	// public
	
	cloudReader.init = function() {
		initHotkeys();
		initScrollEvent();
		loadMoreItems([]);
	}


	// private

	function initHotkeys() {
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

	function initScrollEvent() {
		$(window).scroll(function() {
			var itemsReadByScrolling = $('.item:not(.itemRead)').filter(function (index) {
				var scrolledPastItem = $(window).scrollTop() > $(this).offset().top + 10;
				return scrolledPastItem;
			});
			itemsReadByScrolling.each(function (index, element) {
				$(element).addClass('itemRead');
			});
			if (itemsReadByScrolling.length > 0) {
				var readItemsIds = itemsReadByScrolling.map(function () {
					return this.id;
				}).get();
				loadMoreItems(readItemsIds);
				postItemsRead(readItemsIds);
				switchCurrentItem($('.itemCurrent'), itemsReadByScrolling.last());
			}
		});
	}

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
				newItem.addClass('itemRead');
				var readItemsIds = [newItem[0].id];
				loadMoreItems(readItemsIds);
				postItemsRead(readItemsIds);
			}
			switchCurrentItem(currentItem, newItem);
			newItem[0].scrollIntoView();
		}
	}
	
	function postItemsRead(readItemsIds) {
		$.ajax({
			url: '/items/read',
			type: 'post',
			data: {
				itemsGuids: readItemsIds.join(',')
			}
		});
	}

	function loadMoreItems(readItemsIds) {
		var ITEMS_INITIAL_SIZE = 15;
		var ITEMS_BUFFER_SIZE = 5;
		var unread = $('.item:not(.itemRead)');
		var itemsToLoadCount = ITEMS_INITIAL_SIZE - unread.length;
		if (itemsToLoadCount < ITEMS_BUFFER_SIZE) {
			return;
		}
		var excludedItemsIds = readItemsIds.concat(unread.map(function () {
			return this.id;
		}).get());
		$.ajax({
			url: '/items',
			data: {
				count: itemsToLoadCount,
				excludedItemsGuids: excludedItemsIds.join(',')
			}
		}).done(function (downloadedItems) {
			for (var i = 0; i < downloadedItems.length; i++) {
				if ($('#' + downloadedItems[i].guid).length == 0) {
					var newItem = createNewItem(downloadedItems[i]);
					$('#items').append(newItem);
				}
			}
			$('#message').html(downloadedItems.length > 0 ? '' : 'No more items at the moment.');
		});
	}

	function createNewItem(downloadedItem) {
		var itemHtml = '';
		itemHtml += '<div class="item">\n';
		itemHtml += '    <div class="date"></div>\n';
		itemHtml += '    <div class="title">\n';
		itemHtml += '        <a target="_blank"></a>\n';
		itemHtml += '    </div>\n';
		itemHtml += '    <div>\n';
		itemHtml += '        <a target="_blank" class="feedTitle"></a>\n';
		itemHtml += '        <span class="author"></span>\n';
		itemHtml += '    </div>\n';
		itemHtml += '    <div class="description"></div>\n';
		itemHtml += '</div>';
		var newItem = $.parseHTML(itemHtml);
		newItem[0].id = downloadedItem.guid;
		$('.feedTitle', newItem).html(downloadedItem.feed.title);
		$('.feedTitle', newItem).attr('href', downloadedItem.feed.link);
		var authorContent = downloadedItem.author ? '(author: ' + downloadedItem.author + ')' : '';
		$('.author', newItem).html(authorContent);
		$('.title a', newItem).html(downloadedItem.title);
		$('.title a', newItem).attr('href', downloadedItem.link);
		$('.description', newItem).html(downloadedItem.description);
		$('.date', newItem).html(new Date(parseInt(downloadedItem.sortDate)).format('yyyy-mm-dd HH:MM'));
		return newItem;
	}

	function switchCurrentItem(oldCurrentItem, newCurrentItem) {
		oldCurrentItem.removeClass('itemCurrent');
		newCurrentItem.addClass('itemCurrent');
	}

} (window.cloudReader = window.cloudReader || {}, jQuery));
