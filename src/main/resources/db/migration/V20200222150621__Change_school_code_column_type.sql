alter table department_table drop foreign key FK_depart_school_fk;
alter table school_table modify school_code int auto_increment;
alter table department_table modify school_code int not null;
alter table department_table
    add constraint FK_depart_school_fk
        foreign key (school_code) references school_table (school_code)
            on update cascade;