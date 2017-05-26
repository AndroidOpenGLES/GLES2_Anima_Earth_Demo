uniform mat4 uMVPMatrix; //总变换矩阵
attribute vec4 aPosition;
attribute vec2 aTexCoor;    //顶点纹理坐标
varying vec2 vTextureCoord;  //用于传递给片元着色器的变量

void main()     
{                    	
   gl_Position = uMVPMatrix * aPosition;
   vTextureCoord = aTexCoor;//将接收的纹理坐标传递给片元着色器
}                      