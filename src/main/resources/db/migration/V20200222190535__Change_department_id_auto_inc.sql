alter table class_table drop foreign key FK_class_depart_fk;
alter table teacher_table drop foreign key FK_teacher_depart_fk;
alter table department_table modify department_id int auto_increment;
alter table class_table
    add constraint FK_class_depart_fk
        foreign key (department_id) references department_table (department_id);
alter table teacher_table
    add constraint FK_teacher_depart_fk
        foreign key (department_id) references department_table (department_id)
            on update cascade;


