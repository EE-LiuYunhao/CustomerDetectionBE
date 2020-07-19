CREATE VIEW `AverageStay` AS
(
    SELECT s.`uid`, AVG(StayTime(s.`datetimeIn`, s.`datetimeOut`))
    FROM `StayRecord` AS s
    GROUP BY s.`uid`
);