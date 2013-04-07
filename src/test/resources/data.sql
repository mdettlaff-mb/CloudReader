INSERT INTO Feed (url, title) VALUES ('url1', 'My feed 1');
INSERT INTO Feed (url, title) VALUES ('url2', 'My feed 2');

INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0001', 'url1', 'UNREAD', 'My item 1', now());
INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0002', 'url1', 'UNREAD', 'My item 2', now());
INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0003', 'url2', 'UNREAD', 'My item 3', now());
INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0004', 'url2', 'UNREAD', 'My item 4', now());
INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0005', 'url2', 'UNREAD', 'My item 5', now());
INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0006', 'url2', 'UNREAD', 'My item 6', now());
INSERT INTO FeedItem (guid, feed_url, status, title, downloadDate) VALUES ('item-0007', 'url2', 'READ', 'My item 7', now());
