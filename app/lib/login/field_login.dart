import 'package:flutter/material.dart';

//Autor: Alexsander Melo


class FieldLogin{

  TextEditingController user= new TextEditingController();
  final String USUARIO = 'Usuário: ';


  FieldLogin({this.user});

  Widget get(){
      return new Row(
                children:  <Widget>[
                                new Text(USUARIO,
                                      style: new TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 18,
                                      color: Colors.black)),
                                new Expanded(child: new TextField(controller: user,))
                ],
      );
  }

}