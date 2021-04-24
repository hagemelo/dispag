import 'package:flutter/material.dart';
import './appSkeleton_drawer.dart';
import './appSkeleton_floatingActionButton_novoDebito.dart';
import './appSkeleton_appBar.dart';
import './appSkeleton_body.dart';

//Autor: Alexsander Melo

class Skeleton{

  BuildContext _inputContext;
  AppSkeletonBody _appSkeletonBody;

  Skeleton add({List<Widget> inputConteudo, BuildContext context}){

    _appSkeletonBody = new AppSkeletonBody(inputConteudo);
    _inputContext = context;
    return this;
  }

  Widget createScaffold() {
    return new Scaffold(
        backgroundColor: Colors.lightBlue,
        appBar: new AppSkeletonAppBar(),
        floatingActionButton: new FloatingActionButtonNovoDebito(),
        drawer: new AppSkeletonDrawer(_inputContext),
        body: _appSkeletonBody
    );
  }
}


