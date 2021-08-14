
select * from AIM_MEMBERS;

delete from insurance where member_id ='1234';

select * from insurance;

CREATE TABLE AIM_MEMBERS(
	member_id VARCHAR2(30),
    password VARCHAR2(30) NOT NULL,
	email VARCHAR2(30) NOT NULL,
    ip VARCHAR2(30) NOT NULL,
    CONSTRAINT members_pk PRIMARY KEY(member_id));

CREATE TABLE BABY_INFO(
    parent_id VARCHAR2(30),
    baby_name VARCHAR2(30) NOT NULL,
    birthday VARCHAR2(30) NOT NULL,
    CONSTRAINT baby_info_fk FOREIGN KEY(parent_id)
    REFERENCES aim_members(member_id));

CREATE TABLE baby_diary(
    member_id VARCHAR2(30) NOT NULL,
    baby_name VARCHAR2(30) NOT NULL,
    diary_date VARCHAR2(30) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    contents VARCHAR2(3000),
    CONSTRAINT baby_diary_fk FOREIGN KEY(member_id)
    REFERENCES aim_members(member_id));

CREATE TABLE alarm_log(
    member_id VARCHAR2(30) NOT NULL,
    baby_name VARCHAR2(30) NOT NULL,
    alarm_date VARCHAR2(30) NOT NULL,
    cry_move VARCHAR2(30) NOT NULL,
    contents VARCHAR2(200) NOT NULL,
    CONSTRAINT alarm_log_fk FOREIGN KEY(member_id)
    REFERENCES aim_members(member_id),
    CONSTRAINT alarm_log_ck CHECK(cry_move IN('cry','move')));

CREATE TABLE tips(
    tip VARCHAR2(3000) NOT NULL);
    
CREATE TABLE insurance(
    member_id VARCHAR2(30) NOT NULL,
    u_name VARCHAR2(30) NOT NULL,
    bs_name VARCHAR2(30) NOT NULL,
    b_name VARCHAR2(200) NOT NULL,
    CONSTRAINT insurance_fk FOREIGN KEY(member_id)
    REFERENCES aim_members(member_id));    
    
