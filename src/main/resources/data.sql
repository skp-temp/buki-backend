--INSERT INTO user_badge VALUES (null, 'GachaKing', 3, true, CURRENT_DATE);
-- 부적 완성 뱃지 테스트 데이터 추가 --

-- 수정된 data.sql 예시

INSERT INTO charm (user_id, category, charm_level, goal, alarm_on, alarm_time, alarm_day_type, today_complete, is_valid, complete_at)
VALUES
(1, 'HUSTLE', 21, 'Run 5km', FALSE, '2024-08-27 18:00:00', 'WEEKDAY', FALSE, TRUE, '2024-08-27 18:00:00'),
(1, 'HUSTLE', 21, 'Save $500', FALSE, '2024-08-27 18:00:00', 'WEEKDAY', FALSE, TRUE, '2024-08-27 18:00:00'),
(1, 'HUSTLE', 21, 'Read a book', FALSE, '2024-08-27 18:00:00', 'WEEKDAY', FALSE, TRUE, '2024-08-27 18:00:00'),
(1, 'HUSTLE', 21, 'Yoga for 30 minutes', FALSE, '2024-08-27 18:00:00', 'WEEKDAY', FALSE, TRUE, '2024-08-27 18:00:00'),
(1, 'HUSTLE', 21, 'Complete a course', FALSE, '2024-08-27 18:00:00', 'WEEKDAY', FALSE, TRUE, '2024-08-27 18:00:00');
