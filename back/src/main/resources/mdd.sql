DROP DATABASE IF EXISTS mdd;
CREATE DATABASE mdd;
USE mdd;

DROP TABLE IF EXISTS user;
create table user
(
    id       int auto_increment
        primary key,
    email    varchar(100) not null,
    username varchar(50)  not null,
    password varchar(250) not null,
    constraint email
        unique (email)
);

INSERT INTO mdd.user (id, email, username, password) VALUES (1, 'alice@example.com', 'alice', '$2a$10$QlQE199bFj2M/aIwZU2JnuKut/L2w4oPRCTQypM83lkIasPuG6q/m');
INSERT INTO mdd.user (id, email, username, password) VALUES (2, 'bob@example.com', 'bob', '$2a$10$QlQE199bFj2M/aIwZU2JnuKut/L2w4oPRCTQypM83lkIasPuG6q/m');
INSERT INTO mdd.user (id, email, username, password) VALUES (3, 'charlie@example.com', 'charlie', '$2a$10$QlQE199bFj2M/aIwZU2JnuKut/L2w4oPRCTQypM83lkIasPuG6q/m');
INSERT INTO mdd.user (id, email, username, password) VALUES (4, 'admin@example.com', 'admin', '$2a$10$U8RVDxsCBwcWj/vHgH77veBt7zSM2hVZHOiDuftMEl8OormUsHXQu');
INSERT INTO mdd.user (id, email, username, password) VALUES (5, 'facile@gmail.com', 'J200W', '$2a$10$QlQE199bFj2M/aIwZU2JnuKut/L2w4oPRCTQypM83lkIasPuG6q/m');

DROP TABLE IF EXISTS role;
create table role
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

INSERT INTO mdd.role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO mdd.role (id, name) VALUES (2, 'ROLE_USER');

DROP TABLE IF EXISTS topic;
create table topic
(
    id          int auto_increment
        primary key,
    title       varchar(50) not null,
    description text        not null
);

INSERT INTO mdd.topic (id, title, description) VALUES (1, 'HTML Basics', 'Introduction to HTML');
INSERT INTO mdd.topic (id, title, description) VALUES (2, 'CSS Styling', 'Fundamentals of CSS');
INSERT INTO mdd.topic (id, title, description) VALUES (3, 'JavaScript for Beginners', 'Learning the basics of JavaScript');
INSERT INTO mdd.topic (id, title, description) VALUES (4, 'Advanced JavaScript', 'Deep dive into JavaScript advanced concepts');
INSERT INTO mdd.topic (id, title, description) VALUES (5, 'Responsive Web Design', 'Techniques for responsive web design');
INSERT INTO mdd.topic (id, title, description) VALUES (6, 'Web Performance Optimization', 'Tips to optimize website performance');
INSERT INTO mdd.topic (id, title, description) VALUES (7, 'Web Security Basics', 'Introduction to securing web applications');
INSERT INTO mdd.topic (id, title, description) VALUES (8, 'Front-End Frameworks Overview', 'Comparison of popular front-end frameworks');
INSERT INTO mdd.topic (id, title, description) VALUES (9, 'Backend Development with Node.js', 'Introduction to Node.js for backend development');
INSERT INTO mdd.topic (id, title, description) VALUES (10, 'API Development Best Practices', 'Best practices in developing RESTful APIs');

DROP TABLE IF EXISTS post;
create table post
(
    id       int auto_increment
        primary key,
    title    varchar(100)                       not null,
    content  text                               not null,
    date     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    id_user  int                                not null,
    id_topic int                                not null,
    constraint title
        unique (title),
    constraint FK_post_id_topic
        foreign key (id_topic) references topic (id),
    constraint FK_post_id_user
        foreign key (id_user) references user (id)
);

INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (1, 'Getting Started with HTML', 'HTML (Hypertext Markup Language) is the foundation of web development. It is used to create the structure of web pages by defining elements such as headings, paragraphs, links, and images.<br>In this post, we will explore how to create a basic HTML document.<br>To start, create a new file and save it with a .html extension. Then, write the following code: <!DOCTYPE html><html><head><title>My First HTML Page</title></head><body><h1>Hello, World!</h1></body></html><br>This code defines a basic HTML document with a title and a heading.<br>', '2024-08-13 10:00:00', 1, 1);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (2, 'HTML Elements and Attributes', 'HTML elements are the building blocks of web pages. An element consists of a start tag, content, and an end tag. For example, <p>This is a paragraph.</p> is a paragraph element.<br>Attributes provide additional information about elements. For instance, the <a> tag, which defines a hyperlink, can have an href attribute that specifies the URL.<br>Example: <a href="https://example.com">Click here</a>. This link directs users to the specified URL.<br>In this post, we will explore various HTML elements and their attributes.<br>', '2024-08-14 11:00:00', 2, 1);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (3, 'CSS Selectors and Properties', 'CSS (Cascading Style Sheets) is used to style HTML elements. A CSS rule consists of a selector and a declaration block. The selector targets the HTML element, while the declaration block contains the properties and values that define the styles.<br>Example: p { color: blue; font-size: 16px; }<br>In this example, the p selector targets all paragraph elements, setting their text color to blue and their font size to 16px.<br>In this post, we will dive into different types of CSS selectors and how to use them effectively.<br>', '2024-08-15 12:00:00', 3, 2);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (4, 'Responsive Web Design with Flexbox', 'Responsive web design ensures that web pages look good on all devices, from desktops to mobile phones. Flexbox is a powerful CSS layout module that helps create responsive designs.<br>To use Flexbox, you need to define a container and set its display property to flex. This enables Flexbox for that container, allowing you to control the alignment, direction, and order of the child elements.<br>Example: .container { display: flex; }<br>In this post, we will explore how to create flexible and responsive layouts using Flexbox.<br>', '2024-08-16 13:00:00', 4, 5);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (5, 'JavaScript Data Types', 'JavaScript, a versatile programming language, is fundamental to web development. Understanding its data types is essential.<br>There are several data types in JavaScript, including number, string, boolean, object, and undefined.<br>Example: let age = 30; Here, age is a variable of type number.<br>Another example: let name = "John"; Here, name is a variable of type string.<br>Understanding these types is crucial for writing effective JavaScript code.<br>In this post, we will delve into each of these data types in detail.<br>', '2024-08-17 14:00:00', 5, 3);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (6, 'Understanding JavaScript Closures', 'Closures are an advanced concept in JavaScript, but they are incredibly powerful. A closure is a function that has access to its own scope, the scope of the outer function, and the global scope.<br>Example: function outerFunction() { let outerVariable = I am outside!; function innerFunction() { console.log(outerVariable); } return innerFunction; }<br>In this example, innerFunction has access to outerVariable, even after outerFunction has finished executing.<br>In this post, we will explore closures in depth and how to use them effectively.<br>', '2024-08-18 15:00:00', 1, 4);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (7, 'Improving Website Performance', 'Website performance is critical for user experience and SEO. Slow websites lead to higher bounce rates. To improve performance, consider optimizing images, using a content delivery network (CDN), and minimizing JavaScript.<br>Example: Compress images before uploading them to your site. Tools like TinyPNG or ImageOptim can help.<br>Another example is using lazy loading for images and videos. This technique defers the loading of non-critical resources until they are needed.<br>In this post, we will explore several strategies to optimize website performance.<br>', '2024-08-19 16:00:00', 2, 6);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (8, 'Introduction to Web Security', 'Web security is essential for protecting user data and maintaining the integrity of your website. Common threats include SQL injection, cross-site scripting (XSS), and cross-site request forgery (CSRF).<br>SQL injection is a code injection technique that allows attackers to execute arbitrary SQL queries on your database.<br>XSS allows attackers to inject malicious scripts into web pages viewed by other users.<br>In this post, we will cover these threats in detail and discuss how to protect your website from them.<br>', '2024-08-20 17:00:00', 3, 7);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (9, 'Comparison of React, Vue, and Angular', 'React, Vue, and Angular are three of the most popular front-end frameworks. Each has its strengths and weaknesses.<br>React is known for its flexibility and is widely used for building single-page applications (SPAs).<br>Vue is praised for its simplicity and ease of integration.<br>Angular is a comprehensive framework that includes everything needed to build large-scale applications.<br>In this post, we will compare these frameworks in terms of performance, learning curve, and community support.<br>', '2024-08-21 18:00:00', 4, 8);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (10, 'Introduction to Node.js', 'Node.js is a powerful platform built on Chrome\'s V8 JavaScript engine. It allows developers to build server-side applications using JavaScript.<br>Node.js is known for its non-blocking, event-driven architecture, making it ideal for building scalable network applications.<br>In this post, we will explore the basics of Node.js, including how to set up a development environment and write your first server-side script.<br>', '2024-08-22 19:00:00', 5, 9);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (11, 'Building RESTful APIs with Express', 'Express is a minimal and flexible Node.js web application framework that provides a robust set of features for web and mobile applications.<br>With Express, you can easily create RESTful APIs by defining routes and handling HTTP requests.<br>In this post, we will cover how to set up an Express project, define routes, and connect to a database.<br>By the end, you will be able to build a simple API using Express and Node.js.<br>', '2024-08-23 20:00:00', 1, 10);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (12, 'HTML Forms and Inputs', 'HTML forms are essential for collecting user input. They consist of form elements like <input>, <select>, and <textarea>.<br>Each form element can have various attributes, such as type, name, and value. These attributes define how the form element behaves.<br>Example: <input type="text" name="username"><br>This creates a text input field where users can enter their username.<br>In this post, we will explore the different form elements and how to use them effectively.<br>', '2024-08-24 21:00:00', 2, 1);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (13, 'CSS Grid Layout', 'CSS Grid is a powerful layout system in CSS that allows you to create complex layouts easily. Unlike Flexbox, which is one-dimensional (either row or column), Grid is two-dimensional (rows and columns).<br>To start using Grid, define a container and set its display property to grid.<br>Example: .container { display: grid; grid-template-columns: repeat(3, 1fr); }<br>This creates a grid with three equal-width columns.<br>In this post, we will explore how to create grid layouts and how they can simplify your web designs.<br>', '2024-08-25 22:00:00', 3, 2);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (14, 'JavaScript Promises', 'Promises are a way to handle asynchronous operations in JavaScript. They allow you to write cleaner code without getting trapped in callback hell.<br>A promise has three states: pending, fulfilled, and rejected.<br>Example: let promise = new Promise(function(resolve, reject) { // code });<br>You can use .then() to handle the fulfilled state and .catch() for errors.<br>In this post, we will explore how promises work and how to use them effectively in your JavaScript code.<br>', '2024-08-26 23:00:00', 4, 3);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (15, 'Advanced Responsive Design Techniques', 'Responsive design goes beyond just using media queries. Advanced techniques include fluid grids, flexible images, and CSS Grid/Flexbox.<br>Fluid grids allow your layout to adapt to different screen sizes without breaking.<br>Flexible images resize dynamically to fit the container without distortion.<br>In this post, we will cover these advanced techniques and how to implement them in your designs.<br>', '2024-08-27 09:00:00', 5, 5);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (16, 'Lazy Loading Images', 'Lazy loading is a technique that defers the loading of non-critical resources until they are needed. This can significantly improve website performance.<br>Example: Use the loading="lazy" attribute on <img> tags to enable lazy loading in modern browsers.<br>Another method is using JavaScript to dynamically load images as they come into view.<br>In this post, we will explore different lazy loading techniques and how they can boost your website\'s performance.<br>', '2024-08-28 10:00:00', 1, 6);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (17, 'Protecting Against SQL Injection', 'SQL injection is a common web security vulnerability that allows attackers to manipulate SQL queries by injecting malicious code.<br>To protect against SQL injection, use parameterized queries or prepared statements.<br>Example: SELECT * FROM users WHERE username = ?<br>This ensures that user input is treated as data, not executable code.<br>In this post, we will cover how SQL injection works and how to protect your applications from it.<br>', '2024-08-29 11:00:00', 2, 7);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (18, 'Getting Started with React.js', 'React.js is a JavaScript library for building user interfaces. It is known for its component-based architecture, which allows developers to build complex UIs by combining smaller, reusable components.<br>Example: function App() { return <h1>Hello, World!</h1>; }<br>This is a simple React component that renders a heading.<br>In this post, we will explore the basics of React, including how to create components and manage state.<br>', '2024-08-30 12:00:00', 3, 8);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (19, 'Building APIs with Node.js and Express', 'Node.js and Express are a powerful combination for building APIs. Express provides a simple and flexible way to define routes and handle requests.<br>Example: app.get("/api/users", (req, res) => { res.send("User data"); });<br>This creates a GET endpoint that returns user data.<br>In this post, we will cover how to set up a Node.js project, install Express, and build a simple RESTful API.<br>', '2024-08-31 13:00:00', 4, 9);
INSERT INTO mdd.post (id, title, content, date, id_user, id_topic) VALUES (20, 'Versioning APIs', 'API versioning is essential for maintaining backward compatibility while introducing new features. There are several ways to version APIs, including URI versioning, header versioning, and query parameter versioning.<br>Example: Use URI versioning by including the version number in the endpoint: /api/v1/users.<br>In this post, we will explore different API versioning strategies and how to implement them effectively.<br>', '2024-09-01 14:00:00', 5, 10);

DROP TABLE IF EXISTS subscription;
create table subscription
(
    id       int auto_increment
        primary key,
    id_user  int not null,
    id_topic int not null,
    constraint FK_subscription_id_topic
        foreign key (id_topic) references topic (id),
    constraint FK_subscription_id_user
        foreign key (id_user) references user (id)
);

INSERT INTO mdd.subscription (id, id_user, id_topic) VALUES (7, 5, 10);

DROP TABLE IF EXISTS comment;
create table comment
(
    id      int auto_increment
        primary key,
    id_user int                                not null,
    id_post int                                not null,
    content text                               not null,
    date    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint FK_comment_id_post
        foreign key (id_post) references post (id),
    constraint FK_comment_id_user
        foreign key (id_user) references user (id)
);

INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (1, 1, 1, 'Great introduction to HTML!', '2024-08-13 10:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (2, 2, 1, 'Looking forward to learning more about HTML.', '2024-08-13 11:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (3, 3, 2, 'CSS is so powerful!', '2024-08-15 12:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (4, 4, 2, 'I love styling web pages with CSS.', '2024-08-15 13:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (5, 5, 3, 'Flexbox is a game-changer for responsive design.', '2024-08-16 13:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (6, 1, 3, 'I agree, Flexbox makes layout design much easier.', '2024-08-16 14:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (7, 2, 4, 'JavaScript data types can be tricky to understand.', '2024-08-17 14:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (8, 3, 4, 'I\'ve been practicing with different data types.', '2024-08-17 15:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (9, 4, 5, 'Closures are fascinating!', '2024-08-18 15:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (10, 5, 5, 'I\'m still wrapping my head around closures.', '2024-08-18 16:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (11, 1, 6, 'Website performance is crucial for SEO.', '2024-08-19 16:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (12, 2, 6, 'I\'ve been optimizing my site for better performance.', '2024-08-19 17:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (13, 3, 7, 'Web security is a top priority for me.', '2024-08-20 17:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (14, 4, 7, 'I\'ve implemented security measures on my site.', '2024-08-20 18:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (15, 5, 8, 'I\'m curious to learn more about front-end frameworks.', '2024-08-21 18:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (16, 1, 8, 'Front-end frameworks can speed up development.', '2024-08-21 19:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (17, 2, 9, 'Node.js is a game-changer for backend development.', '2024-08-22 19:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (18, 3, 9, 'I\'ve been building APIs with Node.js.', '2024-08-22 20:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (19, 4, 10, 'RESTful APIs are the future of web development.', '2024-08-23 20:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (20, 5, 10, 'I\'ve been following best practices for API development.', '2024-08-23 21:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (21, 1, 11, 'HTML forms are essential for user interaction.', '2024-08-24 21:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (22, 2, 11, 'I\'ve been working on form validation.', '2024-08-24 22:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (23, 3, 12, 'CSS Grid is a powerful layout system.', '2024-08-25 22:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (24, 4, 12, 'I\'ve been experimenting with CSS Grid layouts.', '2024-08-25 23:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (25, 5, 13, 'Promises have simplified my asynchronous code.', '2024-08-26 23:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (26, 1, 13, 'I\'ve been using promises for API requests.', '2024-08-27 00:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (27, 2, 14, 'Responsive design is crucial for modern websites.', '2024-08-27 09:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (28, 3, 14, 'I\'ve been implementing responsive design techniques.', '2024-08-27 10:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (29, 4, 15, 'Lazy loading has improved my site\'s performance.', '2024-08-28 10:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (30, 5, 15, 'I\'ve been lazy loading images on my site.', '2024-08-28 11:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (31, 1, 16, 'SQL injection is a serious threat to web security.', '2024-08-29 11:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (32, 2, 16, 'I\'ve been securing my site against SQL injection.', '2024-08-29 12:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (33, 3, 17, 'React.js has made building UIs so much easier.', '2024-08-30 12:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (34, 4, 17, 'I\'ve been learning React.js for my projects.', '2024-08-30 13:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (35, 5, 18, 'Node.js and Express are a powerful combination.', '2024-08-31 13:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (36, 1, 18, 'I\'ve been building APIs with Node.js and Express.', '2024-08-31 14:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (37, 2, 19, 'API versioning is crucial for maintaining compatibility.', '2024-09-01 14:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (38, 3, 19, 'I\'ve been versioning my APIs to ensure backward compatibility.', '2024-09-01 15:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (39, 4, 20, 'Looking forward to learning more about React.js.', '2024-09-02 15:30:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (40, 5, 20, 'I\'ve been building APIs with Node.js and Express.', '2024-09-02 16:00:00');
INSERT INTO mdd.comment (id, id_user, id_post, content, date) VALUES (41, 5, 20, 'Test', '2024-08-13 12:29:31');

DROP TABLE IF EXISTS user_role;
create table user_role
(
    id_user int not null,
    id_role int not null,
    constraint user_role_ibfk_1
        foreign key (id_user) references user (id),
    constraint user_role_ibfk_2
        foreign key (id_role) references role (id)
);

create index id_role
    on user_role (id_role);

create index id_user
    on user_role (id_user);

INSERT INTO mdd.user_role (id_user, id_role) VALUES (1, 1);
INSERT INTO mdd.user_role (id_user, id_role) VALUES (2, 2);
INSERT INTO mdd.user_role (id_user, id_role) VALUES (3, 2);
INSERT INTO mdd.user_role (id_user, id_role) VALUES (4, 2);
INSERT INTO mdd.user_role (id_user, id_role) VALUES (5, 2);
