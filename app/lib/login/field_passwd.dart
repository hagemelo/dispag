import 'package:flutter/material.dart';

//Autor: Alexsander Melo


class FieldPasswd{

  TextEditingController passwd= new TextEditingController();
  final String SENHA = 'Senha: ';


  FieldPasswd({this.passwd});

  Widget get(){
    return new Row(
      children:  <Widget>[
        new Text('Senha: ', style: new TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 18,
            color: Colors.black)),
        new Expanded(child: new TextField(controller: passwd, obscureText: true,))
      ],
    );
  }

}