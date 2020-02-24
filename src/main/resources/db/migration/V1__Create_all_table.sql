/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/2/17 12:31:51                           */
/*==============================================================*/

drop table if exists admin_table;

drop table if exists choiceQuestion_table;

drop table if exists curse_table;

drop table if exists source_code_table;

drop table if exists student_table;

drop table if exists class_table;


drop table if exists codeQuestion_table;

drop table if exists exam_table;

drop table if exists judgement_table;

drop table if exists subject_table;

drop table if exists teacher_table;


drop table if exists department_table;

drop table if exists quetionType_table;

drop table if exists school_table;

drop table if exists test_paper_table;

/*==============================================================*/
/* Table: admin_table                                           */
/*==============================================================*/
create table admin_table
(
   admin_no             int(11) not null,
   admin_name           varchar(40),
   admin_password       varchar(255),
   primary key (admin_no)
);

/*==============================================================*/
/* Table: choiceQuestion_table                                  */
/*==============================================================*/
create table choiceQuestion_table
(
   id                   int not null,
   title                text not null comment '题目描述',
   option_A             varchar(255),
   option_B             varchar(255),
   option_C             varchar(255),
   option_D             varchar(255),
   option_E             varchar(255),
   option_F             varchar(255),
   answer               char(1) not null,
   analysis             text,
   level                int comment '难度指数',
   type                 int not null,
   create_teacher       varchar(9),
   primary key (id)
);

alter table choiceQuestion_table comment '存放选择题';

/*==============================================================*/
/* Table: class_table                                           */
/*==============================================================*/
create table class_table
(
   class_id             int not null auto_increment,
   class_no             varchar(4) not null,
   class_name           varchar(20),
   department_id        int not null,
   teacher              varchar(10),
   primary key (class_id)
);

/*==============================================================*/
/* Table: codeQuestion_table                                    */
/*==============================================================*/
create table codeQuestion_table
(
   id                   int(11) not null,
   title                varchar(200) not null,
   description          text,
   input                text,
   output               text,
   sample_input         text,
   hint                 text,
   create_teacher       varchar(9),
   answer               text,
   primary key (id)
);

/*==============================================================*/
/* Table: curse_table                                           */
/*==============================================================*/
create table curse_table
(
   class_no             int not null,
   subject_id           int(11) not null,
   teacher_no           varchar(9),
   primary key (class_no, subject_id)
);

/*==============================================================*/
/* Table: department_table                                      */
/*==============================================================*/
create table department_table
(
   department_id        int not null,
   school_code         int not null,
   department_name      varchar(20),
   primary key (department_id)
);

/*==============================================================*/
/* Table: exam_table                                            */
/*==============================================================*/
create table exam_table
(
   exam_id              int(11) not null auto_increment,
   exam_title           varchar(255),
   start_time           datetime,
   end_time             datetime,
   is_visible           boolean,
   exam_class           text,
   create_teacher       varchar(9),
   create_time          datetime,
   modify_time          datetime,
   primary key (exam_id)
);

/*==============================================================*/
/* Table: judgement_table                                       */
/*==============================================================*/
create table judgement_table
(
   id                   int not null,
   title                varchar(255) not null,
   answer               boolean,
   level                int,
   type                 int,
   create_teacher       varchar(9),
   primary key (id)
);

/*==============================================================*/
/* Table: quetionType_table                                     */
/*==============================================================*/
create table quetionType_table
(
   id                   int not null auto_increment,
   type_name            varchar(50) not null,
   primary key (id)
);

alter table quetionType_table comment '存放问题类型';

/*==============================================================*/
/* Table: school_table                                          */
/*==============================================================*/
create table school_table
(
   school_code         int  not null,
   school_name          varchar(20),
   primary key (school_code)
);

/*==============================================================*/
/* Table: source_code_table                                     */
/*==============================================================*/
create table source_code_table
(
   id                   int(11) not null,
   create_user          varchar(12) not null,
   question_id          int(11),
   source_code          text,
   exam_id              int(11),
   primary key (id)
);

/*==============================================================*/
/* Table: student_table                                         */
/*==============================================================*/
create table student_table
(
   student_no           varchar(12) not null,
   name                 varchar(10),
   sex                  bit,
   phone                char(11),
   class_id             int not null,
   password             varchar(100),
   primary key (student_no)
);

alter table student_table comment '学生信息';

/*==============================================================*/
/* Table: subject_table                                         */
/*==============================================================*/
create table subject_table
(
   curse_id             int(11) not null,
   curse_name           varchar(100),
   teacher_no           varchar(9) not null,
   primary key (curse_id, teacher_no)
);

/*==============================================================*/
/* Table: teacher_table                                         */
/*==============================================================*/
create table teacher_table
(
   teacher_no           varchar(9) not null,
   department_id        int,
   teacher_name         varchar(10),
   phone                char(11),
   email                varchar(20),
   password             varchar(100),
   primary key (teacher_no)
);

/*==============================================================*/
/* Table: test_paper_table                                      */
/*==============================================================*/
create table test_paper_table
(
   question_id          int(11) not null auto_increment,
   Column_2             char(10),
   primary key (question_id)
);

alter table choiceQuestion_table add constraint FK_choiceOpt_teacher_fk foreign key (create_teacher)
      references teacher_table (teacher_no) on delete restrict on update cascade;

alter table choiceQuestion_table add constraint FK_choiceOption_type_fk foreign key (type)
      references quetionType_table (id) on delete restrict on update cascade;

alter table class_table add constraint FK_class_depart_fk foreign key (department_id)
      references department_table (department_id) on delete restrict on update cascade;

alter table class_table add constraint FK_class_teacher_fk foreign key (teacher)
      references teacher_table (teacher_no) on delete set null on update cascade;

alter table codeQuestion_table add constraint FK_codeQuestion_teacher_fk foreign key (create_teacher)
      references teacher_table (teacher_no) on delete restrict on update restrict;

alter table curse_table add constraint FK_Reference_17 foreign key (subject_id, teacher_no)
      references subject_table (curse_id, teacher_no) on delete restrict on update restrict;

alter table curse_table add constraint FK_Reference_18 foreign key (class_no)
      references class_table (class_id) on delete restrict on update restrict;

alter table department_table add constraint FK_depart_school_fk foreign key (school_code)
      references school_table (school_code) on delete restrict on update cascade;

alter table exam_table add constraint FK_exam_teacher_fk foreign key (create_teacher)
      references teacher_table (teacher_no) on delete restrict on update restrict;

alter table judgement_table add constraint FK_judgement_teacher_fk foreign key (create_teacher)
      references teacher_table (teacher_no) on delete restrict on update restrict;

alter table judgement_table add constraint FK_judgement_type_fk foreign key (type)
      references quetionType_table (id) on delete restrict on update cascade;

alter table source_code_table add constraint FK_sourceCode_codeQue_fk foreign key (question_id)
      references codeQuestion_table (id) on delete restrict on update restrict;

alter table source_code_table add constraint FK_sourceCode_exam_fk foreign key (exam_id)
      references exam_table (exam_id) on delete restrict on update restrict;

alter table source_code_table add constraint FK_sourceCode_student_fk foreign key (create_user)
      references student_table (student_no) on delete restrict on update restrict;

alter table student_table add constraint FK_student_class_fk foreign key (class_id)
      references class_table (class_id) on delete restrict on update cascade;

alter table subject_table add constraint FK_curse_teacher_fk foreign key (teacher_no)
      references teacher_table (teacher_no) on delete restrict on update cascade;

alter table teacher_table add constraint FK_teacher_depart_fk foreign key (department_id)
      references department_table (department_id) on delete restrict on update cascade;

