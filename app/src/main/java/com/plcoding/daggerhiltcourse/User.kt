package com.plcoding.daggerhiltcourse

class User() {
    //konstruktor musi byc bez argumentów
    var name: String? = ""
    var age: Int = -1
    var age2: Int? = -1//nadmiarowa zmienna pozostaje bez zmian bo nie dało sie jej przypisać

    //jesli jest niezgodność typów to wywali zapliakcji np Int z firebase do Stringa w modelu
    //zmienna moze być nawet val to nie przeszkadza

    //jesli zmeinna jest np Int zamiast Int? to i tak edytor nie pozwoli dojsc do sytuacji zeby
// nie przypisać zmiennej bo tym bardziej musze dać domyslną zmienna
    
}

