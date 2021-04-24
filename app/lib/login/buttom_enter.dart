import 'package:flutter/material.dart';

//Autor: Alexsander Melo


class ButtomEnter{

  final Function action;

  ButtomEnter({this.action});

  Widget get(){
    return new Padding(
      padding: new EdgeInsets.all(12.0),
      child: new ElevatedButton(
          style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(Colors.blueGrey),
          ),
          onPressed: action,
          child: new Text('Entrar')),
    );
  }


}