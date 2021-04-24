import 'package:flutter/material.dart';
//Autor: Alexsander Melo


class AppSkeletonBody extends StatelessWidget {

  List<Widget> _inputConteudo;

  AppSkeletonBody(this._inputConteudo);

  @override
  Widget build(BuildContext context) {
    return new Container(
      padding: new EdgeInsets.all(32.0),
      child: new Center(
        child: new Column(
            children: _inputConteudo
        ),
      ),
    );
  }
}