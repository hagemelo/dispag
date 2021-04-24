import 'package:flutter/material.dart';
import './field_login.dart';
import './field_passwd.dart';
import './buttom_enter.dart';

//Autor: Alexsander Melo

class BodyLogin{
  final String NAME_APP = "D I S P A G";
  final String IMAGEM = 'images/finance.png';
  final List<Widget> elementsBody = <Widget>[];
  TextEditingController user= new TextEditingController();
  TextEditingController pass= new TextEditingController();
  final Function action;

  BodyLogin({this.action}){
    elementsBody.addAll([
                        new Image.asset(IMAGEM),
                        new Text(NAME_APP,
                                  style: new TextStyle(fontWeight: FontWeight.bold,
                                                        fontSize: 32,
                                                        color: Colors.black87)
                        ),
                        new Card(
                            child: new Container(
                                color: Colors.white54,
                                padding: new EdgeInsets.all(20.0),
                                child: new Column(
                                    children: [
                                      new FieldLogin(user: user).get(),
                                      new FieldPasswd(passwd: pass).get(),
                                      new ButtomEnter(action: action).get()
                                    ]
                                )
                            )
                        )
                ]);
  }

  Widget get(){
    return new Container(
      padding: new EdgeInsets.all(32.0),
      child: new Center(
        child: new Column(
          children: elementsBody,
        ),
      ),
    );
  }
}