import 'package:flutter/material.dart';
import 'package:app/login/login.dart';
import 'package:app/home/home.dart';

//Autor: Alexsander Melo


//void main() => runApp( new MaterialApp(home: new LoginApp()));
void main() => runApp( new MaterialApp(
    //home: new LoginApp(),
    initialRoute: LoginApp.LOGIN,
    routes: {
      LoginApp.LOGIN: (ctx) => LoginApp(),
      HomeApp.HOME: (ctx) => HomeApp(),
    },
    onGenerateRoute:(settings){ return new MaterialPageRoute(builder: (context) => HomeApp());},
    onUnknownRoute: (settings){ return new MaterialPageRoute(builder: (context) => LoginApp());},
));


