
const columns = document.querySelectorAll(".column");
columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class"
    });

    column.addEventListener("drop", (e) => {
        e.preventDefault();
        console.log('username', e.currentTarget.querySelector(".username").textContent);
        console.log('gamename', e.currentTarget.querySelector(".gamename").textContent);
        console.log('target', e.currentTarget.closest('div').getAttribute("id"));
    });
});

var main={
    init : function (){
        var _this = this;

        $('#btn-save').on('click', function (){
            var _team_array = [];
            var team = {};

            $('div.container div').each(function(index, item) {
                if(item.className == "column" && item.id.length > 0) {
                    console.log("item", item, item.id);
                    $("#"+item.id + ' span').each(function(index2, item2){
                        let cnt = index2 + 1;
                        if(item2.className == "username")
                            team.username = item2.textContent;
                        else
                            team.gamename = item2.textContent;
                        if((cnt % 2) == 0)
                            _team_array.push({team : item.id, username : team.username, gamename : team.gamename});
                    });
                }
            });

            _this.save(_team_array);
        })
    },
    save : function (_team_array){
        $.ajax({
            type: 'POST',
            url: '/api/teambuilding',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(_team_array)
        }).done(function (){
            alert('저장되었습니다.')
        }).fail(function (error){
            alert(JSON.stringify(error));
        })

    }
}

main.init();




