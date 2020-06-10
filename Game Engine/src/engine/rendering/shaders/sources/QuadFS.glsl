#version 440 core

out vec4 color;

in vec4 v_color;
in vec2 v_uv;

uniform sampler2D sampler;

void main() {
	color = texture(sampler, v_uv) * v_color;
}