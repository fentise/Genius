export const cookie = {
        set: function setCookie( name, value, days ) {
                var myDay = new Date();
                 myDay.setTime( myDay.getTime() + days*3600*24*1000 );
                if (value === '' || value === null) { }
                else {
                   document.cookie = name + "=" + escape(value) +";expires="+ myDay.toGMTString();  //设置cookie
                }
              },

         get: function( key ){//获取cookie方法
                /*获取cookie参数*/
                var getCookie = document.cookie.replace(/[ ]/g,"");  //获取cookie，并且将获得的cookie格式化，去掉空格字符
                var arrCookie = getCookie.split(";")  //将获得的cookie以"分号"为标识 将cookie保存到arrCookie的数组中
                //使用for循环查找cookie中的key
                for(let i = 0; i < arrCookie.length; i++){
                    var arr = arrCookie[i].split("=");   //将单条cookie用"等号"为标识，将单条cookie保存为arr数组
                    if( key == arr[0]){    //匹配变量名称，其中arr[0]是指的cookie名称，如果该条变量为tips则执行判断语句中的赋值操作
                         return  unescape(arr[1]);      //直接返回key对应的值
                    }
                }
                return null;   //如果key不存在，则返回null
             },

         delete: function( key ){    //删除cookie方法  2
            var date = new Date();     //获取当前时间
            date.setTime( date.getTime() - 10000 );     //将date设置为过去的时间
            document.cookie = key + "=v; expires =" + date.toGMTString();  //设置cookie
         }
    }
