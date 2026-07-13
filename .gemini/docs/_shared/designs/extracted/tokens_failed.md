# Token Inventory: stitch_failed.html

## Tailwind Config Overrides

_(none found)_

## Global Styles

Inline `<style>` rules that apply globally — these affect every matching element regardless of class list.

```css
.material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 48;
        }
        body {
            font-family: 'Manrope', sans-serif;
            background-color: #FFFBF0; /* Specific brand background from requirements */
        }
        .error-vibration {
            animation: subtle-shake 0.5s cubic-bezier(.36,.07,.19,.97) both;
        }
        @keyframes subtle-shake {
            10%, 90% { transform: translate3d(-1px, 0, 0); }
            20%, 80% { transform: translate3d(2px, 0, 0); }
            30%, 50%, 70% { transform: translate3d(-4px, 0, 0); }
            40%, 60% { transform: translate3d(4px, 0, 0); }
        }
body {
      min-height: max(884px, 100dvh);
    }
```

## Motion Inventory

Captured animation vocabulary. Bucket each token via the Web-Motion Policy in `.claude/skills/_shared/motion.md`: **KEEP** the 4 non-interaction families (Ambient bg, Loading/Attention loop, Entrance, Value-driven) + honor reduced-motion; **DROP** all touch press (`active:*`, ripple) and pointer/hover (`hover:*`, `group-hover:*`) feedback. Per-element `animate-*` / `transition-*` / `active:` / `hover:` tags are annotated inline in the Elements section below.

### @keyframes (<style> blocks)

- subtle-shake

### JS animation drivers

- timed step (Value/Entrance)
- count-up number (Value-driven)

## Elements

Each class is followed by its deterministic token interpretation when one applies (e.g. `mt-4 → margin-top: 16dp`). Classes with no annotation are layout primitives, state variants, or unrecognised — interpret them yourself.

**Element formats:**
- **Visual elements** (any class converts to a visual token, or has inline style) get a full block with one line per class.
- **Layout-only elements** (only structural classes like `flex`, `items-center`, `justify-between`) get a single compact line — they still appear in order so structural mismatches (Row vs Column, arrangement, alignment) remain visible.
- **Classless text children** (e.g. `<span>Label</span>` inside a button) also appear as a one-liner with their text, so sibling DOM order inside a flex container is preserved — compare it against the Compose content lambda order.

- [1] `<html>` `light`
### <!-- Material Symbols Outlined -->

**[2] `<body>`**

1. `min-h-screen` → min-height: 100vh/vw
2. `flex`
3. `flex-col`
4. `items-center`
5. `justify-center`
6. `p-6` → padding: 24dp
7. `text-on-surface`
8. `overflow-hidden`

### <!-- Subtle Background Atmosphere -->

**[3] `<div>`**

1. `fixed` (positioning: fixed — Compose: Box overlay or BottomBar slot)
2. `inset-0` → inset: 0dp
3. `opacity-20` → opacity: 0.2
4. `pointer-events-none`

### <!-- Main Content Container -->

**[4] `<main>`**

1. `relative`
2. `z-10` (z-index — Compose has no z-index; layering is order-based)
3. `flex`
4. `flex-col`
5. `items-center`
6. `text-center`
7. `max-w-sm`
8. `w-full` → width: 100%
9. `space-y-8` → children spaced 32dp vertically

### <!-- Icon Section -->

- [5] `<div>` `relative group`
### <!-- Decorative Glow -->

**[6] `<div>`**

1. `absolute` (positioning: absolute — Compose: Box overlay or BottomBar slot)
2. `inset-0` → inset: 0dp
3. `bg-error/10`
4. `blur-3xl`
5. `rounded-full` → corner-radius: CircleShape
6. `scale-150`
7. `transition-transform` (motion: transition hint — Entrance/Value family)
8. `duration-700` (motion: transition hint — Entrance/Value family)
9. `group-hover:scale-125` (motion: web-only — DROP — pointer/hover)

**[7] `<div>`**

1. `relative`
2. `w-24` → width: 96dp
3. `h-24` → height: 96dp
4. `rounded-full` → corner-radius: CircleShape
5. `bg-error-container/30`
6. `flex`
7. `items-center`
8. `justify-center`
9. `border` → border-width: 1dp
10. `border-error/10`
11. `error-vibration`

**[8] `<span>` — "warning"**

1. `material-symbols-outlined`
2. `text-6xl` → font-size: 60sp
3. `text-[#B3261E]` → color: #B3261E
- _inline style_: `font-variation-settings: 'FILL' 1;`

### <!-- Text Content -->

**[9] `<div>`**

1. `space-y-3` → children spaced 12dp vertically

**[10] `<h1>` — "Something went wrong"**

1. `text-[32px]` → font-size: 32sp
2. `font-semibold` → font-weight: 600 (SemiBold)
3. `text-[#4E4730]` → color: #4E4730
4. `leading-tight` → line-height: 1.25× font-size
5. `tracking-tight` → letter-spacing: -0.025em (× font-size for sp)
6. `font-headline`

**[11] `<p>` — "We're having trouble connecting to the safety servers. Pleas..."**

1. `text-on-surface-variant`
2. `font-body`
3. `text-base` → font-size: 16sp
4. `leading-relaxed` → line-height: 1.625× font-size
5. `max-w-[280px]` → max-width: 280px
6. `mx-auto` → horizontal margin: auto

### <!-- Action Section -->

**[12] `<div>`**

1. `w-full` → width: 100%
2. `pt-4` → top padding: 16dp

**[13] `<button>`**

1. `w-full` → width: 100%
2. `py-4` → vertical padding: 16dp
3. `px-8` → horizontal padding: 32dp
4. `bg-[#6B5000]` → background: #6B5000
5. `text-[#FFFFFF]` → color: #FFFFFF
6. `rounded-lg` → corner-radius: 8dp
7. `font-semibold` → font-weight: 600 (SemiBold)
8. `text-lg` → font-size: 18sp
9. `shadow-lg` → shadow: ~8dp elevation
10. `shadow-primary/20`
11. `hover:bg-primary` (motion: web-only — DROP — pointer/hover)
12. `active:scale-95` (motion: interaction — DROP — touch press feedback)
13. `transition-all` (motion: transition hint — Entrance/Value family)
14. `duration-300` (motion: transition hint — Entrance/Value family)
15. `flex`
16. `items-center`
17. `justify-center`
18. `space-x-2` → children spaced 8dp horizontally

**[14] `<span>` — "refresh"**

1. `material-symbols-outlined`
2. `text-xl` → font-size: 20sp

- [15] `<span>` — "Retry"
### <!-- Secondary Silent Action -->

**[16] `<button>` — "Contact Support"**

1. `mt-4` → top margin: 16dp
2. `w-full` → width: 100%
3. `py-2` → vertical padding: 8dp
4. `text-on-surface-variant`
5. `font-medium` → font-weight: 500 (Medium)
6. `text-sm` → font-size: 14sp
7. `hover:text-primary` (motion: web-only — DROP — pointer/hover)
8. `transition-colors` (motion: transition hint — Entrance/Value family)

### <!-- Footer Identity -->

**[17] `<footer>`**

1. `fixed` (positioning: fixed — Compose: Box overlay or BottomBar slot)
2. `bottom-8` → bottom: 32dp
3. `text-center`
4. `opacity-40` → opacity: 0.4

**[18] `<p>` — "UGZeroHarm Security"**

1. `text-xs` → font-size: 12sp
2. `font-bold` → font-weight: 700 (Bold)
3. `tracking-[0.2em]` → letter-spacing: 0.2em
4. `uppercase`
5. `text-on-surface-variant`

---
**Total elements**: 18 | **Visual**: 15 | **Layout-only**: 3 | **Total classes**: 99 | **Auto-converted**: 47 (47%)

