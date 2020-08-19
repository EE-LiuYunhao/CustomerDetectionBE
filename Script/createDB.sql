CREATE TABLE `UserInfo`
(
    `uid` INT(11) NOT NULL AUTO_INCREMENT,
    `gender` NCHAR(1),
    `name` NVARCHAR(100) NOT NULL,
    PRIMARY KEY(`uid`)
);
CREATE TABLE `EncodedFace`
(
    `faceId` INT(15) NOT NULL AUTO_INCREMENT,
    `uid` INT(11) NOT NULL,
    `timeStamp` DATETIME NOT NULL,
    `encodedFacePath` NVARCHAR(200) NOT NULL,
    PRIMARY KEY(`faceId`),
    FOREIGN KEY(`uid`) REFERENCES UserInfo(`uid`)
);
CREATE TABLE `FaceImgPath`
(
    `imgId` INT(15) NOT NULL AUTO_INCREMENT,
    `uid` INT(11) NOT NULL,
    `faceId` INT(15) NOT NULL,
    `ImgPath` NVARCHAR(200) NOT NULL,
    PRIMARY KEY(`imgId`),
    FOREIGN KEY(`faceId`) REFERENCES `EncodedFace`(`faceId`),
    FOREIGN KEY(`uid`) REFERENCES `UserInfo`(`uid`)
);
CREATE TABLE `StayRecord`
(
    `recordId` INT(15) NOT NULL AUTO_INCREMENT, 
    `datetimeIn` DATETIME NOT NULL,
    `datetimeOut` DATETIME,
    `uid` INT(11) NOT NULL,
    PRIMARY KEY(`recordId`),
    FOREIGN KEY(`uid`) REFERENCES UserInfo(`uid`)
);
