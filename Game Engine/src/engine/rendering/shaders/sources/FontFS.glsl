#version 330 core

out vec4 color;

in vec4 v_color;
in vec2 v_uv;
in vec2 v_widthEdge;

uniform sampler2D sampler;

float width = 0.48;
float edge = 0.04;

void main() {
	float dist = 1.0 - texture(sampler, v_uv).a;
	float alpha = 1.0 - smoothstep(v_widthEdge.x, v_widthEdge.x + v_widthEdge.y, dist);
	color = vec4(v_color.rgb, alpha) * v_color.a;
}