import 'package:flutter/material.dart';
//Autor: Alexsander Melo


class AppSkeletonDrawer extends StatelessWidget {

  BuildContext _inputContext;
  AppSkeletonDrawer(this._inputContext);

  @override
  Widget build(BuildContext context) {
    return new Drawer(
      child: new Container(
        padding: new EdgeInsets.all(32.0),
        child: new Column(
          children: <Widget>[
            new Text('Hello Drawer'),
            new ElevatedButton(onPressed: () => Navigator.pop(_inputContext),
                child: new Text('Close'))
          ],
        ),
      ),
    );
  }
}