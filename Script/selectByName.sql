DELIMITER $$
CREATE PROCEDURE SelectByName(userName NVARCHAR(100))
BEGIN
    SELECT f.`faceId` as FaceID, f.`encodedFacePath` as FacePath, i.`imgId` as ImageId, i.`ImgPath` as ImagePath
    FROM
    (
        SELECT `faceId`, `encodedFacePath`, `uid`
        FROM `EncodedFace`
        WHERE `uid` IN (SELECT `uid` FROM `UserInfo` WHERE `name` = userName)
    ) AS f
    INNER JOIN
    (
        SELECT `faceId`, `ImgPath`, `uid`, `imgId`
        FROM `FaceImgPath`
        WHERE `uid` IN (SELECT `uid` FROM `UserInfo` WHERE `name` = userName)
    ) AS i
    ON 
    f.`uid` = i.`uid`
    AND
    f.`faceId` = i.`faceId`;
END$$
DELIMITER ;
