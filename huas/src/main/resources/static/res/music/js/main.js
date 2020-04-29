/*
  1:歌曲搜索接口
    请求地址:https://autumnfish.cn/search
    请求方法:get
    请求参数:keywords(查询关键字)
    响应内容:歌曲搜索结果

  2:歌曲url获取接口
    请求地址:https://autumnfish.cn/song/url
    请求方法:get
    请求参数:id(歌曲id)
    响应内容:歌曲url地址
  3.歌曲详情获取
    请求地址:https://autumnfish.cn/song/detail
    请求方法:get
    请求参数:ids(歌曲id)
    响应内容:歌曲详情(包括封面信息)
  4.热门评论获取
    请求地址:https://autumnfish.cn/comment/hot?type=0
    请求方法:get
    请求参数:id(歌曲id,地址中的type固定为0)
    响应内容:歌曲的热门评论
  5.mv地址获取
    请求地址:https://autumnfish.cn/mv/url
    请求方法:get
    请求参数:id(mvid,为0表示没有mv)
    响应内容:mv的地址
*/
var vm=new Vue({
	el:"#player",
	data:{
		//搜索词
		keywords:'',
		//歌曲列表
		songMsglist:[],
		//歌曲连接地址
		musicUrl:'',
		//歌曲封面
		musicPic:'',
		//歌曲评论
		musicCommentlist:[],
		isPlay:false,
		//歌曲movie地址
		movieUrl:'',
		isShow:false
		},
	methods:{
				//歌曲搜索
		searchMusic:function(){
		var that=this;
		  
			axios.get("https://autumnfish.cn/search?keywords="+this.keywords)
					.then(function(response){
						//console.log(response.data.result.songs);
						
						that.songMsglist=response.data.result.songs;
					},
					function(err){	})
			
		},
		//歌曲播放 歌曲获取
		playMusic:function(id){
               var that=this;
			axios.get("https://autumnfish.cn/song/url?id="+id)
			.then(function (response){
			//	console.log(response.data.data[0].url);
            that.musicUrl=response.data.data[0].url; 
				
			},function(err){});
			//歌曲详情获取
			axios.get("https://autumnfish.cn/song/detail?ids="+id)
			.then(function(response){
				that.musicPic=response.data.songs[0].al.picUrl;
			//	console.log(response.data.songs[0].al.picUrl);
			//	console.log(response);
				
			},function(err){});
			//歌曲详情获取
		axios.get("https://autumnfish.cn/comment/hot?type=0&id="+id)
		.then(function(response){
		console.log(response.data.hotComments);
	    that.musicCommentlist=response.data.hotComments;
			
	    	},function(err){});
		
		},
		//播放音乐
		play:function(){
			
			this.isPlay=true;
			
			
		},
		//暂停音乐
		pause:function(){
			
			this.isPlay=false;
			
		},
	   //播放视频插件
	   playMovie:function(id){
		   var that=this;
		   this.isShow=true;
		  axios.get("https://autumnfish.cn/mv/url?id="+id)
		   .then( function(response){
			  
			  // console.log(response.data.data.url);
			   that.movieUrl=response.data.data.url;
			   
		   },function(err){
			   
		   })
		   
		   
	   },
	   //隐藏视频
	   hide:function(){
		   this.isShow=false;
		   this.movieUrl='';
	   }
		
		
	}
	
	
	
	
})
