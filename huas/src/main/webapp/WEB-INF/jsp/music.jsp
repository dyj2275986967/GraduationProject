<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="z" uri="/huas/system"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />
  <title>放松一下</title>
  <!-- 样式 -->
<!--springboot改图标-->
  <link href="${ctx }/res/img/favicon.ico" type="image/x-icon" rel="icon">
  <link rel="stylesheet" href="${ctx }/res/music/css/index.css">
</head>

<body>
  <div class="wrap">
    <!-- 播放器主体区域 -->
    <div class="play_wrap" id="player">
      <div class="search_bar">
        <span style="margin-left:50px;font-size:20px;color:white">文理轻松听</span>
		<img src="" alt="" />
        <!-- 搜索歌曲 -->
        <input type="text" autocomplete="off" v-model="keywords" @keyup.enter="searchMusic"/>
      </div>
      <div class="center_con">
        <!-- 搜索歌曲列表 -->
        <div class='song_wrapper'>
          <ul class="song_list">
           <li v-for="item in songMsglist"><a href="javascript:;" @click="playMusic(item.id)"></a> <b>{{item.name}}</b> 
		   <span v-if="item.mvid!=0" @click="playMovie(item.mvid)"><i></i></span></li>

          </ul>
          <img src="${ctx }/res/music/images/line.png" class="switch_btn" alt="">
        </div>
        <!-- 歌曲信息容器 -->
        <div class="player_con " v-bind:class="{playing:isPlay}" >
          <img src="${ctx }/res/music/images/player_bar.png" class="play_bar" />
          <!-- 黑胶碟片 -->
          <img src="${ctx }/res/music/images/disc.png" class="disc autoRotate" />
          <img :src="musicPic" class="cover autoRotate" />
        </div>
        <!-- 评论容器 -->
        <div class="comment_wrapper">
          <h5 class='title'>热门留言</h5>
          <div class='comment_list' >
            <dl  v-for="item in musicCommentlist" >
              <dt><img :src="item.user.avatarUrl" alt=""></dt>
              <dd class="name">{{item.user.nickname}}</dd>
              <dd class="detail">
             {{item.content}}
              </dd>
            </dl>
          </div>
          <img src="${ctx }/res/music/images/line.png" class="right_line">
        </div>
      </div>
      <div class="audio_con" >
        <audio ref='audio' @play="play()" @pause="pause()" v-bind:src="musicUrl" controls autoplay loop class="myaudio"></audio>
      </div>
      <div class="video_con" v-show="isShow" style="display: none;">
        <video v-bind:src="movieUrl" controls="controls"></video>
        <div class="mask" @click="hide()" ></div>
      </div>
    </div>
  </div>
  <!-- 开发环境版本，包含了有帮助的命令行警告 -->
  <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
  <!-- 官网提供的 axios 在线地址 -->
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <!-- 引入自定义的main -->
    <script src="${ctx }/res/music//js/main.js"></script>
</body>

</html>