/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. edit
 */
var app = (function () {
    var cinema;
    var functions = [];
    function recargar(cine) {
        for (var i = 0; i < functions.length; i++) {
            var numsit= count(functions[i].seats)
            var flag = "<tr><td>" + cine[0].name + "</td><td>" +
                    functions[i].movie.name + "</td><td>" + numsit + "</td><td>" +
                    functions[i].date + "</td></tr>";
            $("#cuadro").append(flag);
        }
        function count(sill) {
            var cont = 0;
            for (var i = 0; i < sill.length; i++) {
                for (var j = 0; j < sill[i].length; j++) {
                    if (sill[i][j] == true) {
                        cont++;
                    }
                }
            }
            return cont;
        }
    }
    var getCinema = function () {
        var name = $("#name").val();
        console.log(name);
        var callback = function (cine) {
            cinema = cine;

        }
        var funciones = function (cine) {
            console.log(cine);
            functions = cine[0].functions;
            recargar(cine);
        }
        var func = apimock.getCinemaByName(name, funciones);
       
    }

    return{
        getCinema: getCinema
    };

})();
