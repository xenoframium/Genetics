#version 400

in float dotProd;
flat in uint renderStage;

out vec4 colour;

uniform vec4 inColour;
uniform float brightness;

void main(){
    vec4 inc = inColour;
    if (renderStage == 1) {
        inc = vec4(255/255.0f, 180/255.0f, 100/255.0f, 1);
    }
    colour = vec4(clamp((inc.xyz * clamp(sqrt(dotProd), 0.4, 1.0))*brightness, 0, 1), inc.w);
}
