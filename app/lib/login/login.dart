
import '../home/home.dart';
import 'package:flutter/material.dart';
import './body_login.dart';
import 'package:http/http.dart' as http;
import 'dart:convert' ;


//Autor: Alexsander Melo
class LoginApp extends StatefulWidget {

  static const String LOGIN= '/';

  @override
  _State createState() => new _State();
}

class _State extends State<LoginApp>{

  BodyLogin _bodyLogin;
  var _isLoading = false;

  void goToHome(BuildContext context){
    Navigator.of(context).pushReplacementNamed(HomeApp.HOME, arguments: {
      'user': _bodyLogin.user.value.text.toString(),
      'token': 'asdasdasdas'
    });
  }

  void sendAutentication(BuildContext context) async{
    String url = 'http://10.0.2.2:3000/dev/login';
    Map<String, String> headers = {"Content-Type": "application/json"};
    var body = json.encode({
      "user": _bodyLogin.user.value.text.toString(),
      "senha": _bodyLogin.pass.value.toString(),
    });

    http.post(url, body: body, headers: headers)
        .then((value) => actionList(value, context));
  }

  Set<void> actionList(http.Response value, BuildContext context) {
    return {
        print(value.body),
        setState(() {
        _isLoading = false;
        }),
        goToHome(context)
    };
  }

  void actionEnter(BuildContext context){
    setState(() {_isLoading = true;});
    sendAutentication(context);
  }

  @override
  Widget build(BuildContext context){

    _bodyLogin = new BodyLogin(action:()=> actionEnter(context));
    return new Scaffold(
        backgroundColor: Colors.lightBlue,
        body: _isLoading? new Center(child: CircularProgressIndicator(),) :_bodyLogin.get()
    );
  }

}