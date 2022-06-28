"use strict"

// Exercise 1:

var number = 2013

function reverseNumber (n) {
    
    let loopingNumber = n.toString().split("").reverse().join("")
    
    console.log(loopingNumber)
}

reverseNumber(number);

// Exercise 2:

var alphabetical = "esternocleidomastoideo"

function sortWord (w) {

    let sortingNumber = w.split("").sort().join("")

    console.log(sortingNumber)
}

sortWord(alphabetical)

// Exercise 3:

// primer planteamiento del problema (planteamiento incompleto/fallido)

// function upping (pharap) {
    
    //     let cutted = pharap.split(" ")
    
    //     cutted.forEach(function (cutted){
        
        //         let kekw = (cutted.charAt(0).toUpperCase() + cutted.slice(1)).join("");
        
        //         console.log(kekw)
        //     })
        // }
        
var text1 = "prince of persia"


const upping = (pharap) => {

    pharap = pharap.split(" ").map(function(sectionOfPharap){

        return sectionOfPharap.charAt(0).toUpperCase() + sectionOfPharap.slice(1)}).join(" ")

    console.log(pharap)
}

upping(text1)

// Exercise 4:

var pharapToCount = "Me duele el esternocleidomastoideo"

function countting (text) {
    
    let largestWord = ""
    
    let cutText = text.replace(",", " ").split(" ")
    
    cutText.forEach(function (word){
        
        if (word.length>largestWord.length) {
            
            largestWord=word
        }
        
        return largestWord
    });
    
    console.log(largestWord)
}

countting(pharapToCount)