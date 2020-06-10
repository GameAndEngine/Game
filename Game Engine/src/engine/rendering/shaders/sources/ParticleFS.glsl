#version 440 core

out vec4 color;

in vec4 v_color;
in vec2 v_uv1;
in vec2 v_uv2;

in float v_blend;

uniform sampler2D sampler;

void main() {
	vec4 currentColor = texture(sampler, v_uv1);
	vec4 nextColor = texture(sampler, v_uv2);
	
	color = mix(currentColor, nextColor, v_blend) * v_color;
}