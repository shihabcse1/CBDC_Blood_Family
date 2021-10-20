<h1 align="center">
    <img src="assets/images/logo/TolkProjectIcon.ai.svg" />
</h1>

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/facebook/react/blob/main/LICENSE)

<h1 align="center">
    CBDC Blood Bank
</h1>

<p>Tolk is a single page chat web application developed using <a href="https://github.com/django/django">
django web framework</a> and JavaScript.<br/>It uses 
<a href="https://github.com/django/channels">django channles</a> and JavaScript WebSockets to send messages,
notifications,
and status updates. it also comes with two themes Dark Purple and White Light.<br/>
You can check it out in <a href="http://tolk-project.herokuapp.com/">demo</a>.
</p>
<br/>

## Features

- [x] Register/Login Feature
- [x] English/Bangla Language Support
- [x] Find and Search Blood Donors
- [x] Add Blood Request
- [x] Add Blood Bank
- [x] Find Blood Bank
- [x] Add Ambulance/Helpline
- [x] Find Amblulance/Helpline
- [x] Become a Blood Donor
- [x] Singout Feature


## ScreenShots
<img src="screenshots/Screenshot%20from%202021-08-08%2018-06-11.png" alt="dark_theme"/>
<img src="screenshots/Screenshot%20from%202021-08-08%2018-21-48.png" alt="login"/>
<img src="screenshots/Screenshot%20from%202021-08-08%2018-23-00.png" alt="light_theme"/>

## Running Tolk locally

clone the repo

```
git clone https://github.com/MuhammadSalahAli/TolkProject.git
```

enter the project's folder

```
cd TolkProject
```

checkout the development branch

```bash
git checkout devleopment
```

install dependencies

```bash
pip install -r requirements.txt
```

now run the development server

```bash
python manage.py runserver
```

visit <a href="http://127.0.0.1:8000/">http://127.0.0.1:8000/</a> and you will see the tolk preloader.

## Frontend library

The tolk fronted is built using a custom js library in the folder assets/js/app/silly. The library simply splits html
code into templates (i call them components in code) and then renders them using the <a href="https://ejs.co/">ejs</a>
template engine. There is also an interface for sending ajax and websocket messages. it's meant to be used to rapidly 
convert
static html pages into dynamically rendered ones.<br/>
<br/>
<img src="Documentation/JsAppClassdiagram.jpg" alt="silly_class_diagram"  width="700" height="700"/>
<p>
A UML Class Diagram For Silly.
</p>
