import 'package:flutter/material.dart';
//Autor: Alexsander Melo



class FloatingActionButtonNovoDebito extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new FloatingActionButton(
      onPressed: null,
      backgroundColor: Colors.blueGrey,
      mini: true,
      child: new Icon(Icons.add_circle),
    );
  }
}