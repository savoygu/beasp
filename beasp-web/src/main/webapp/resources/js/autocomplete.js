define(function(require, exports, module){

  $.fn.autocomplete=function(trigobj){
    if($(".autoul")){
      $(".autoul").remove();
    }
    autoComplete(this);
  }

  var autoComplete=function(trigobj){
    //初始化邮箱列表
      var mail = new Array(
          "qq.com",
          "163.com",
          "126.com",
          "gmail.com",
          "sina.com",
          "aliyun.com",
          "hotmail.com",
          "sohu.com",
          "vip.126.com",
          "vip.163.com",
          "vip.qq.com",
          "vip.sina.com",
          "sina.cn",
          "vip.163.com",
          "msn.com",
          "outlook.com",
          "live.com",
          "live.cn",
          "yahoo.com.cn",
          "yahoo.cn",
          "yahoo.com.tw",
          "21cn.com",
          "tom.com"
      );

      var tmp=[];
      for(var i=0,les=mail.length;i<les;i++){
        tmp.push({
          'ext': mail[i],
          'index': i
        });
      }

      var autolist=$("<ul class='autoul'></ul>");
     
      for(var i=0,j=tmp.length;i<j;i++){       
        var $liElement = $("<li data-key="+tmp[i].index +" class=\"autoli\"><span class=\"ex\"></span><span class=\"at\">@</span><span class=\"step\">"+tmp[i].ext+"</span></li>");
        $liElement.appendTo($(autolist));
      }
      $(autolist).insertAfter(trigobj);
    
      $(autolist).hide();

  
   ///在邮箱输入框输入字符
  $(trigobj).off("keyup.auto").on("keyup.auto",function(event){

  
    if(event.keyCode!=38 && event.keyCode!=40 && event.keyCode!=13){

      if($.trim(this.value).match(/[^@]@/)==null){//输入了不含"@"的字符或者"@"开头
      
        if($.trim(this.value).match(/^@/)==null){
          //不以"@"开头
          $(this).siblings('ul').children("li").children(".ex").text($(this).val());
        }
      }else{
        //输入字符后，第一次出现了不在首位的"@"
        //当首次出现@之后，有2种情况：1.继续输入；2.没有继续输入 当继续输入时
        var str = $.trim($(this).val());
        var strs = str.split("@");
        $(".ex").text(strs[0]);//"@"之前输入的内容
        var len = strs[0].length;//"@"之前输入内容的长度
        if(str.length>len+1){
     
          //截取出@之后的字符串,@之前字符串的长度加@的长度,从第(len+1)位开始截取
          var strright = str.substr(len+1);
     
          //正则屏蔽匹配反斜杠"\"
          if(strright.match(/[\\]/)!=null){
            strright.replace(/[\\]/,"");
            return false;
          }
     
          //遍历li
          $(autolist).children('li').each(function(){
            var step = $(this).find('span.step');
            //对@后的输入进行匹配
            if( step.text().match(strright)!=null && step.text().indexOf(strright)==0){
              if(!step.parent().hasClass("showli")){
                  step.parent().addClass('showli');
              }
              if(strright.length>=step.text().length){
                  step.parent().removeClass("showli").removeClass("lihover");
              }
            }else{
              step.parent().removeClass("showli");
            }
          });

          $(autolist).find('li.showli').eq(0).addClass('lihover');
        }else{
          $(autolist).find('li').each(function(i,n){
              $(this).removeClass('lihover');
              if(!$(this).hasClass('showli')){
                $(this).addClass('showli');
              }
              if(i==0){
                $(this).addClass('lihover');
              }
          });
        }
      }

      if( $.trim($(this).val())!="" && $.trim(this.value).match(/^@/)==null ) {
          //console.log($(autolist).find('.showli').length);

        if($(autolist).find('.showli').length!==0){
            $(autolist).show(0);
        }else{
            $(autolist).hide(0)
        }
          

        //同时去掉原先的高亮，把第一条提示高亮
        $(autolist).find(".lihover").removeClass("lihover").eq(0).addClass("lihover");

      }else{//如果为空或者"@"开头
        $(autolist).hide();
        $(autolist).find("li:eq(0)").removeClass("lihover");
      }

    }//有效输入按键事件结束

    
    //按键为backspace(8)或是delete(46)
    if(event.keyCode == 8 || event.keyCode == 46){
   
       $(this).next().children().removeClass("lihover");
       $(this).next().children("li:visible:eq(0)").addClass("lihover");
     }//删除事件结束
    
    //按键为方向键↑(38)
    if(event.keyCode == 38){
         //使光标始终在输入框文字右边
        $(this).val($(this).val());
     }//方向键↑结束
     
     //回车键(13)
     if(event.keyCode == 13){
        if($(autolist).children('li').is(".lihover")) {

          $(trigobj).val($(autolist).find(".lihover").children(".ex").text() + "@" + $(autolist).find(".lihover").children(".step").text());
        }
     
        $(autolist).children().hide();
        $(autolist).children().removeClass("lihover"); 
        $(autolist).hide();
        //$(trigobj).focus();//回车后输入栏获得焦点
        $(trigobj).blur();
    }
    
    
  })
  $(trigobj).keydown(function(event){
    //按键↓时....
    if(event.keyCode == 40){

        var hoverItem=$(autolist).find(".lihover");
        
        
        if ($(autolist).children('li').is(".lihover")) {

            //如果下一个到了最后，返回
            if($(hoverItem).attr("data-key")==($(autolist).children("li").length)-1){
              return false;
            }else{

              if(hoverItem.nextAll().hasClass("showli")){
                $(hoverItem).removeClass("lihover").nextAll(".showli:eq(0)").addClass("lihover");
                //当为第四个时，就把前面的插入到后面，以能滚动的选择
                if($(hoverItem).index()==3){
                      $(autolist).append($(autolist).children("li").eq(0));

                  }

                }else{

                $(hoverItem).removeClass("lihover");
                $(autolist).find("li.showli").eq(0).addClass("lihover");
              }
            }
        }

    }
  
    //按键↑时....
    if(event.keyCode == 38){
  
        var hoverItem=$(autolist).find(".lihover");
      
      if($(autolist).children('li').is(".lihover")){

          if($(hoverItem).attr("data-key")==0){
              return false;
            }else{


                if($(hoverItem).index()==0){

                    $(autolist).prepend($(autolist).children("li").eq(($(autolist).find(".showli").length)-1));

                }else{

                  $(hoverItem).removeClass("lihover").prev(".showli:eq(0)").addClass("lihover");
                }

            }
          //如果下一个到了第一个，返回
           /*if($(hoverItem).attr("data-key")==0){
              return false;
            }else{

              if(hoverItem.prevAll("li").hasClass("showli")){
                
                $(hoverItem).removeClass("lihover").prev(".showli:eq(0)").addClass("lihover");
                //当为第一个时，就把后面的插入到前面，以能滚动的选择
                
                console.log($(hoverItem).index());

                if($(hoverItem).index()==1){
                  
                      $(autolist).before($(autolist).children("li").eq(($(autolist).find(".showli").length)-1));
                  
                  }

                }else{
                  console.log($(hoverItem).index())

                $(hoverItem).removeClass("lihover");
                $(autolist).find("li.showli").eq(($(autolist).find(".showli").length)-1).addClass("lihover");
              }

            }*/


      }



    }


  })

  $("body").click(function(){
    $(autolist).hide();
  });
   //鼠标点击document事件结束
  
  //鼠标划过li时
  $(autolist).find('li').hover(function(){
  
      if($(autolist).find('li').hasClass("lihover")){
          $(autolist).find('li').removeClass("lihover");
      }
      $(this).addClass("lihover");
  });

  
  //鼠标点击li时
  $(autolist).find('li').click(function(){
  
    if($(autolist).find('li').hasClass("lihover")){
   
      $(autolist).find('li').removeClass("lihover");
    }

    $(this).addClass("lihover");
    $(trigobj).val($(autolist).find(".lihover").children(".ex").text() + "@" + $(autolist).find(".lihover").children(".step").text());
      $(autolist).children().removeClass("lihover"); 
      $(trigobj).blur();//回车后输入栏获得焦点
    });
  }


//}
});