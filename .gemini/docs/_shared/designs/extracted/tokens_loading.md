# Token Inventory: stitch_loading.html

## Tailwind Config Overrides

_(none found)_

## Global Styles

Inline `<style>` rules that apply globally — these affect every matching element regardless of class list.

```css
@keyframes m3-rotate {
            100% { transform: rotate(360deg); }
        }
        @keyframes m3-dash {
            0% { stroke-dasharray: 1, 200; stroke-dashoffset: 0; }
            50% { stroke-dasharray: 89, 200; stroke-dashoffset: -35px; }
            100% { stroke-dasharray: 89, 200; stroke-dashoffset: -124px; }
        }
        .m3-spinner {
            animation: m3-rotate 2s linear infinite;
        }
        .m3-spinner-circle {
            stroke-dasharray: 1, 200;
            stroke-dashoffset: 0;
            stroke-linecap: round;
            animation: m3-dash 1.5s ease-in-out infinite;
        }
        body {
            background-color: #FFFBF0; /* Explicitly requested hex */
            font-family: 'Manrope', sans-serif;
        }
body {
      min-height: max(884px, 100dvh);
    }
```

## Motion Inventory

Captured animation vocabulary. Bucket each token via the Web-Motion Policy in `.claude/skills/_shared/motion.md`: **KEEP** the 4 non-interaction families (Ambient bg, Loading/Attention loop, Entrance, Value-driven) + honor reduced-motion; **DROP** all touch press (`active:*`, ripple) and pointer/hover (`hover:*`, `group-hover:*`) feedback. Per-element `animate-*` / `transition-*` / `active:` / `hover:` tags are annotated inline in the Elements section below.

### @keyframes (<style> blocks)

- m3-rotate, m3-dash

### Keyframe magnitudes

Animated value ranges (the delta each animation moves through). Pin these in the blueprint's `## Motion` **Magnitude** column — they are the only source for scale/translate/opacity/offset amounts (duration/easing come from the shorthand above; the implementer must not invent magnitudes).

- **m3-rotate**: rotate 360deg
- **m3-dash**: stroke-dashoffset 0→-35→-124

## Elements

Each class is followed by its deterministic token interpretation when one applies (e.g. `mt-4 → margin-top: 16dp`). Classes with no annotation are layout primitives, state variants, or unrecognised — interpret them yourself.

**Element formats:**
- **Visual elements** (any class converts to a visual token, or has inline style) get a full block with one line per class.
- **Layout-only elements** (only structural classes like `flex`, `items-center`, `justify-between`) get a single compact line — they still appear in order so structural mismatches (Row vs Column, arrangement, alignment) remain visible.
- **Classless text children** (e.g. `<span>Label</span>` inside a button) also appear as a one-liner with their text, so sibling DOM order inside a flex container is preserved — compare it against the Compose content lambda order.

**[1] `<html>`**

1. `h-full` → height: 100%

**[2] `<body>`**

1. `h-full` → height: 100%
2. `flex`
3. `items-center`
4. `justify-center`
5. `overflow-hidden`

### <!-- Minimal Loading Container -->

- [3] `<div>` `relative flex flex-col items-center`
### <!-- Material 3 Circular Progress Indicator -->

**[4] `<div>`**

1. `w-12` → width: 48dp
2. `h-12` → height: 48dp

**[5] `<svg>`**

1. `m3-spinner`
2. `w-full` → width: 100%
3. `h-full` → height: 100%

- [6] `<circle>` `m3-spinner-circle`
### <!-- Optional micro-text to reinforce brand identity -->

**[7] `<div>`**

1. `mt-6` → top margin: 24dp
2. `opacity-0` → opacity: 0
3. `animate-[fadeIn_1s_ease-out_forwards_0.5s]` (motion: looping '[fadeIn_1s_ease-out_forwards_0.5s]' — looping (family: infer from keyframes))

**[8] `<p>` — "Initializing Safety Protocols"**

1. `text-on-surface-variant`
2. `font-label`
3. `text-sm` → font-size: 14sp
4. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
5. `uppercase`

---
**Total elements**: 8 | **Visual**: 6 | **Layout-only**: 2 | **Total classes**: 24 | **Auto-converted**: 10 (41%)

