# java-spring-website

Overview

\* My plan is to deploy this web-app on a permanent domain which would allow me to link the project in various places, but as of September 2021 this project is still a work in progress as I still need to finish writing up the HTML pages that describe my projects on the website and add CSS beyond the basic layout. The code is here for anyone who wants to run it locally and test out the functionality. The back-end is as complete as I want it to be for this foray into full-stack programming, but I will be sporadically working up and updating the front-end throughout the school year (even though I hear second-year engineering is highly demanding). When I deploy the website, I will link it here.

The following is a summer project that I completed during the summer after my first year of Engineering Science at U of T. I have had previous experience working in Python and C from both my academic studies as well as extracurricular learning before university, as well as a bare minumum of experience working with databases. However, I had never tried Java, HTML/CSS, Javascript, or worked in any framework like Spring. I put about six weeks of work into this website as of the start of my second year of engineering in September 2021, but the first few weeks were spent familiarizing myself with Java, HTML/CSS, as well as working alongside a few worked examples of rudimentary Spring MVC and Spring Boot web apps before starting with my own web app.

The highlights of this projects are:
 - User database with log-in and admin/user functionality
 - Storage of user information including addresses, emails, phone numbers, and the permissions of the user.
 - Spring Security and Thymeleaf to grant different permissions to users with different roles.
 - A project table that contains information about and links to other personal projects I have done.
 - The ability to add, view, edit, and delete user information and project information, stored in a MySQL database.
 - Limited HTML/CSS demonstration with a few examples of Javascript and even one example of Ajax.

Future plans as of September 2021:
- Write up the About Me section on the website and finish my HTML page write-ups on my projects. 
- Finish the front-end and gradually add CSS as I find the time to do so.
- Permanently link projects in the sidebar so they are still accessible even if people play around with and delete the project entries in the database.
- Deploy on a permanent domain once the front-end is satisfactorily functional.
- Link this GitHub page on the website when it is deployed and link the webapp on this GitHub page.
- Link my LinkedIn on the website and the website on GitHub

The web app stores users, user information, and information about other projects I have done in a MySQL database. The user log-in functionality uses Spring Security and I have added two roles. In the below SQL statements, you can see that when I set up the webaspp initially, I insert a user with both admin and user roles, as well as a user with only the guest role. If you run this locally or after I have deployed it on a domain, you can log in with either user and see the differences. Some actions, such as editing and deleting some entries, are only available to admins, while all users can still do limited actions such as change their own password and their own user information. 

If you wish to test the editing and deleting functionalities, please make a new user or a new project and play around with that or use the code to launch a local version. I will permanently link all my projects in the navigation sidebar so they are always accessible, but the only item of concern is if someone changes the password for the admin user, then others won't be able to log in as an admin and test the admin functions.

Please do email me at khu6244@gmail.com with questions, concerns, bugs, feedback, anything!


Admin user: "khu6244@gmail.com" (my actual email)

User with only the "user" role: "guest@email.com" (not my email)

Password for both: "q1w2e3" (obviously not my real password)

SQL setup statements

Use these to set up the tables upon creating a local instance of this webapp.

INSERT INTO `projects_kevin.user` (`id`,`email`,`password`,`username`) VALUES (1,'khu6244@gmail.com','$2a$10$.BU0h2psmOPcwa8p4FLhw.vCLLpeap91IYZWmFLQ2REBxk3P9UsOK','khu6244');

INSERT INTO `projects_kevin.user` (`id`,`email`,`password`,`username`) VALUES (2,'guest@email.com','$2a$10$.BU0h2psmOPcwa8p4FLhw.vCLLpeap91IYZWmFLQ2REBxk3P9UsOK','guest');

INSERT INTO `role` (`id`,`name`) VALUES (1,'ADMIN');

INSERT INTO `role` (`id`,`name`) VALUES (2,'USER');

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 1);

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 2);

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (2, 1);

INSERT INTO project (id, description, name, link)
VALUES (1, 'I built a custom mechanical keyboard from scratch. I learned about AutoCAD, Altium, Arduinos, soldering, and an open-source firmware called QMK.', 'Mechanical Keyboard', 'keyboard');

INSERT INTO project (id, description, name, link)
VALUES (2, 'I built a wooden coffee table with my dad, using 2x3s, 1x3s, and a plywood tabletop. We used dowels and applied stain and polyurethane at the end.', 'Wooden Coffee Table', 'table');

INSERT INTO project (id, description, name, link)
VALUES (3, 'I built a Java web app using the Spring framework with MySQL. In fact, you're looking at it right now!', 'Java Web App', 'website');


Credit to https://github.com/batuhaniskr as the skeleton of this project is loosely based on his Spring Boot Product System.
