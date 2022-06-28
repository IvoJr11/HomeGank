"use strict"

var age = 19;

var ignasiAge = 32;

var ageDiff = age - ignasiAge;

console.log (ageDiff);

if (age<21) {
    console.log ("You are not older than 21");
} else {
    console.log ("You are older than 21");
}

if (age<32) {
    console.log ("Ignasi is older than you");
} else { if (age>32) {
    console.log ("Ignasi is younger than you");
} else {
    console.log ("You have the same age as Ignasi");
}
}