DELIMITER $$
CREATE FUNCTION StayTime(startTime DATETIME , endTime DATETIME)
RETURNS NUMERIC
BEGIN
    -- DECLARE nonNullEndTime DATETIME;
    -- DECLARE stayLength NUMERIC;
    SET @nonNullEndTime = IFNULL(endTime, NOW());
    SET @stayLength = TIMESTAMPDIFF(MINUTE, startTime, @nonNullEndTime);
    RETURN @stayLength;
END $$
DELIMITER ;