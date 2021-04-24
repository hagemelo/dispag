import 'package:flutter/material.dart';
//Autor: Alexsander Melo


class AppSkeletonAppBar extends AppBar{

  static Widget _createTitle(){
    return new Center(
        child: new Column(
          children: <Widget>[
            new Text('D I S P A G',
              style: new TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 24,
                  color: Colors.black87),
              textAlign: TextAlign.left,
            ),
          ],
        )
    );
  }

  AppSkeletonAppBar(): super(title: _createTitle(), backgroundColor:  Colors.blueGrey);

}