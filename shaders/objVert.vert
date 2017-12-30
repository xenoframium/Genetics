#version 400

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 normal;
layout(location = 2) in uint stage;

uniform mat4 mvp;

out float dotProd;
flat out uint renderStage;

vec3 lightDir = normalize(vec3(-1.0f, 1.0f, -1.0f));

void main(){
    gl_Position = mvp * vec4(vertexPosition, 1.0);
    renderStage = stage;
    dotProd = dot(normalize((mvp * vec4(normal, 0.0)).xyz), lightDir);
}
