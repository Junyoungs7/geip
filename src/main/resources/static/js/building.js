
const columns = document.querySelectorAll(".column");
columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class"
    });

    column.addEventListener("drop", (e) => {
        e.preventDefault();
        console.log('name1', e.currentTarget.querySelector(".name1").textContent);
        console.log('name2', e.currentTarget.querySelector(".name2").textContent);
        console.log('target', e.currentTarget.closest('div').getAttribute("id"));
    });
});

var main={
    init : function (){
        var _this = this;
        $('#btn-save').on('click', function (){
            console.log("btn click....");
            $('span.name1').forEach(function(index) {
                console.log(index);
            });
            //console.log($('span.name1').length);
            //_this.save();
        })
    },
    save : function (){
        var data = {
            username: $('#username').val(),
            gamename: $('#gamename').val()};

        $.ajax({
            type: 'POST',
            url: '/api/teambuilding',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (){
            alert('저장되었습니다.')
            console.log('name1' ,data.username);
            console.log('name2' ,data.gamename);
        }).fail(function (error){
            alert(JSON.stringify(error));
        })

    }
}

main.init();




