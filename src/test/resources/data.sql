INSERT INTO Feed (url, title) VALUES ('url1', 'My feed 1');
INSERT INTO Feed (url, title) VALUES ('url2', 'My feed 2');

INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0001', 'url1', FALSE, 'My item 1');
INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0002', 'url1', FALSE, 'My item 2');
INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0003', 'url2', FALSE, 'My item 3');
INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0004', 'url2', FALSE, 'My item 4');
INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0005', 'url2', FALSE, 'My item 5');
INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0006', 'url2', FALSE, 'My item 6');
INSERT INTO FeedItem (guid, feed_url, read, title) VALUES ('item-0007', 'url2', TRUE, 'My item 7');
