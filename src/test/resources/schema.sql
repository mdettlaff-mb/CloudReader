CREATE TABLE Feed (
	url VARCHAR(500) NOT NULL PRIMARY KEY,
	title VARCHAR(255),
	link CLOB
);

CREATE TABLE FeedItem (
	guid VARCHAR(255) NOT NULL PRIMARY KEY,
	feed_url VARCHAR(500) NOT NULL,
	title VARCHAR(255),
	link CLOB,
	description CLOB,
	date TIMESTAMP,
	downloadDate TIMESTAMP NOT NULL,
	author VARCHAR(255),
	uri VARCHAR(255),
	status VARCHAR(16) NOT NULL,
	FOREIGN KEY(feed_url) REFERENCES Feed(url)
);

CREATE TABLE dictionary (
	key VARCHAR(32) NOT NULL PRIMARY KEY,
	value CLOB NOT NULL
);
