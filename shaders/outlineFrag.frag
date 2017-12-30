#version 400

in vec2 uv;

out vec4 colour;

uniform vec4 fgColour;
uniform vec4 bgColour;
uniform float brightness;

uniform sampler2D sampler;

void main(){
    if (texture(sampler, uv).rgb == vec3(0, 0, 0)) {
        colour = bgColour * brightness;
    } else {
        colour = fgColour * brightness;
    }
}
