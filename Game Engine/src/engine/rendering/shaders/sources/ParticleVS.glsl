#version 440 core

layout(location = 0) in vec2 a_position;
layout(location = 1) in vec4 a_color;
layout(location = 2) in vec2 a_uv1;
layout(location = 3) in vec2 a_uv2;
layout(location = 4) in float a_blend;

out vec4 v_color;
out vec2 v_uv1;
out vec2 v_uv2;

out float v_blend;

uniform mat4 projection;
uniform mat4 view;

void main() {
	gl_Position = projection * view * vec4(a_position, 0, 1);
	v_color = a_color;
	v_uv1 = a_uv1;
	v_uv2 = a_uv2;
	v_blend = a_blend;
}