// 日期转换
export default function formatDate (millinSeconds) {
    var date = new Date(millinSeconds);
    var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
    var suffix = new Array("st", "nd", "rd", "th");

    var year = date.getFullYear(); //年
    var month = monthArr[date.getMonth()]; //月
    var ddate = date.getDate(); //日
    var hh = date.getHours()
    var mm = date.getMinutes()
    var ss = date.getSeconds()
    console.log(hh, mm, ss, 'aaa');
    // if (ddate % 10 < 1 || ddate % 10 > 3) {
    //     ddate = ddate + suffix[3];
    // } else if (ddate % 10 == 1) {
    //     ddate = ddate + suffix[0];
    // } else if (ddate % 10 == 2) {
    //     ddate = ddate + suffix[1];
    // } else {
    //     ddate = ddate + suffix[2];
    // }
    return month + " " + ddate + " " + "" + hh + ":" + mm;
}