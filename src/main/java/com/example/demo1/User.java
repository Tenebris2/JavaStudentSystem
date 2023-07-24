package com.example.demo1;

public class User {
        private String name;
        private String Student_ID;
        private String Birthday;

        public User(String Student_ID_, String name_,String birthday)
        {
            this.name = name_;
            this.Student_ID = Student_ID_;
            this.Birthday = birthday;
        }
        public String getName()
        {
            return name;
        }
        public String getStudent_ID()
        {
            return Student_ID;
        }
        public String getBirthday()
        {
        return Birthday;
        }
}
