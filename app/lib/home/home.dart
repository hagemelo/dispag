import 'package:flutter/material.dart';
import 'package:app/default/appSkeleton.dart';

//Autor: Alexsander Melo

class HomeApp extends StatelessWidget {

  static const String HOME= '/home';

  List<Widget> _createHomeConteudo(String user){
    List<Widget> lista = [];
    lista.add(new Text('Usuário: ${user}' , style: new TextStyle(
        fontWeight: FontWeight.bold,
        fontSize: 18,
        color: Colors.black)));
    return lista;
  }

  @override
  Widget build(BuildContext context) {
    final routArgs = ModalRoute.of(context).settings.arguments as Map<String, String>;
    final String user = routArgs['user'];

    Skeleton skeleton  = new Skeleton().add(
                          inputConteudo: _createHomeConteudo(user),
                          context: context);
    return new Scaffold(
        backgroundColor: Colors.lightBlue,
        body: skeleton.createScaffold()
    );
  }

}

