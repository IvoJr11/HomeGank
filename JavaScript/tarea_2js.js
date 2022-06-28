"use strict"

// SORTING AN ARRAY
// exercise 1:

var names = ["Arena Valentino", "Aragon Santiago", "Berguesio Guille", "Branko Haberkon", "Camila Domato", "Casile Esteban", "Cardielo Caro", "Collao Daphne", "Dambolena Facundo", "Eduardo Mendoza", "Ferrero Ignacio", "Flores Ana", "Ordoñez Dylan", "Olea Martin", "Ortiz Ariel", "Pascal Ivo", "Priotto Ezequiel", "Zarate Lucas"]

names.sort();

console.log (names[0]);
console.log (names[names.length-1]);

let i=0
for (i=0; i<names.length; i++) {
    console.log (names[i]);
}

// LOOPING OVER AN ARRAY
// exercise 2:

var ages = [19, 23, 26, 34, 29, 25, 29, 22, 23, 27, 21, 19, 26, 31, 19, 18, 19]

let j=0;

// while (j<ages.length) {
//     if (ages[j]%2 == 0) {
//         console.log (`${ages[j]}`);
//     }
//     j++;
// }

for (j==0; j<ages.length; j++) {
    if (ages[j]%2 == 0) {
        console.log (`${ages[j]}`);
    }
}

// FUNCTIONS THAT USE ARRAYS
// exercise 3:

var numbers = [1, 27, 2, 34, 31, 6, 78, 12, 90, 12, 9, 0]

function lowest () {
    let numLow = Math.min (...numbers)
    console.log (numLow)
}

lowest ()

// exercise 4:

var numbers2 = [1, 27, 2, 34, 31, 6, 78, 12, 90, 12, 9, 0]

function biggest () {
    let numBig = Math.max (...numbers2)
    console.log (numBig)
}

biggest ()

// exercise 5:

var numbers3 = [1, 27, 2, 34, 31, 6, 78, 12, 90, 12, 9, 0];

var n = 1;

var index = n;

function position (arrayOfNumbers, n) {
    console.log(arrayOfNumbers[n]);
}

position(numbers3, 3);

// exercise 6:

var numbers4 = [1, 27, 2, 34, 31, 6, 78, 12, 90, 2, 27, 1];

function numbersRepeat (arrx) {

    var k;

    var arrv = []

    for (k=0; k<arrx.length; k++) {

        for(var i=k+1; i<arrx.length; i++) {

            if (arrx[k]===arrx[i]) {

                if (!arrv.includes(arrx[k])) {
   
                    arrv.push(arrx[k])

                }

            }
        }
    }

    console.log(arrv)
}

numbersRepeat(numbers4)

// exercise 7:

var myColor = ["Red", "Green", "White", "Black"]

function goToString (arraicito) {

    console.log(arraicito.toString());

}

goToString(myColor)